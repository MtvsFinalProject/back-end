package com.project.final_project.airecommendation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.final_project.airecommendation.domain.AIRecommendation;
import com.project.final_project.airecommendation.dto.AIResponseDTO;
import com.project.final_project.airecommendation.dto.RecommendResponseDTO;
import com.project.final_project.airecommendation.dto.UserRecomendByInterestRequestDTO;
import com.project.final_project.airecommendation.repository.AIRecommendationRepository;
import com.project.final_project.chatlog.domain.ChatLog;
import com.project.final_project.school.domain.School;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.repository.UserRepository;
import com.project.final_project.user.service.UserService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.batch.item.Chunk;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class AIRecommendationService {

  private final AIRecommendationRepository aiRecommendationRepository;
  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final UserRepository userRepository;

  private static final String AI_CHAT_URL = "https://certainly-legal-grubworm.ngrok-free.app/chat_logs/";
  private static final String AI_INTEREST_URL = "https://certainly-legal-grubworm.ngrok-free.app/save_interests/";
  private static final String AI_RECOMMNED_URL = "https://certainly-legal-grubworm.ngrok-free.app/recommend/";

  // 20개의 ChatLog 메시지를 객체 형태로 추출하여 AI 서버에 전송
  public AIResponseDTO sendChatLogToAI(List<String> chatLogs, Long senderId) {

    Integer isenderId = senderId.intValue();

    // 메시지를 객체 형태로 추출
    List<Map<String, String>> messages = chatLogs != null && !chatLogs.isEmpty() ?
        chatLogs.stream()
            .map(chatLog -> Map.of("message", chatLog))
            .collect(Collectors.toList())
        : new ArrayList<>();

    // AI 서버에 보낼 요청 바디 생성
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("senderId", isenderId);  // senderId 설정
    requestBody.put("messages", messages);  // 메시지 리스트 전송

    // RestTemplate를 사용하여 동기식 요청 수행
    String responseBody;
    try {
      responseBody = restTemplate.postForObject(AI_CHAT_URL, requestBody, String.class);
      // 응답 본문 처리 코드
    } catch (HttpServerErrorException e) {
      log.error("AI 서버 오류 발생: {}", e.getResponseBodyAsString());
      throw new RuntimeException("AI 서버 오류 발생", e);
    }

    System.out.println(responseBody);

    // JSON 응답 처리
    AIResponseDTO aiResponse;
    try {
      // 응답을 먼저 JsonNode로 변환
      JsonNode rootNode = objectMapper.readTree(responseBody);

      // recommended_users 필드가 없는지 확인
      if (!rootNode.has("recommended_users") && rootNode.get("message") != null) {
        log.info("첫 채팅 로그 임베딩 완료 {}", responseBody);
        aiResponse = null;
      }
      else {
        // 정상적으로 recommended_users가 있을 경우 처리
        aiResponse = objectMapper.treeToValue(rootNode, AIResponseDTO.class);
      }

    } catch (JsonProcessingException e) {
      throw new RuntimeException("응답 JSON 파싱 중 오류 발생", e);
    }

    return aiResponse;
  }

  public AIResponseDTO sendInterestToAI(UserRecomendByInterestRequestDTO dto) {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("senderId", dto.getUserId());
    requestBody.put("interests", dto.getInterest());

    // RestTemplate를 사용하여 동기식 요청 수행
    String responseBody;
    try {
      responseBody = restTemplate.postForObject(AI_INTEREST_URL, requestBody, String.class);
      // 응답 본문 처리 코드
    } catch (HttpServerErrorException e) {
      log.error("AI 서버 오류 발생: {}", e.getResponseBodyAsString());
      throw new RuntimeException("AI 서버 오류 발생", e);
    }

    // 빈 Chunk 생성
    List<String> emptyChatLogChunk = new ArrayList<>();

    AIResponseDTO aiResponseDTO = sendChatLogToAI(emptyChatLogChunk, dto.getUserId().longValue());

    System.out.println(aiResponseDTO);

    return aiResponseDTO;
  }

  public void saveRecommendation(AIRecommendation recommendation) {
    System.out.println("recommendations = " + recommendation);
    aiRecommendationRepository.save(recommendation);
  }

  public List<RecommendResponseDTO> getRecommendationInfoList(Integer userId) {

    List<RecommendResponseDTO> res = new ArrayList<>();

    List<AIRecommendation> all = aiRecommendationRepository.findAIRecommendationListByUserId(userId); // senderId, message, similarity

    all.forEach(
        ar -> {
          User recommendedUser = userRepository.findById(ar.getRecommendedUserId()).orElseThrow(
              () -> new IllegalStateException("not fonnd user id : " + ar.getRecommendedUserId()));
          RecommendResponseDTO rrDTO = getRecommendResponseDTO(ar, recommendedUser);
          res.add(rrDTO);
        }
    );

    return res;
  }

  private static RecommendResponseDTO getRecommendResponseDTO(AIRecommendation ar, User recommendedUser) {
    School recommendedUserSchool = recommendedUser.getSchool();

    RecommendResponseDTO rrDTO = new RecommendResponseDTO(
        recommendedUser.getId(),
        recommendedUser.getName(),
        ar.getSimilarity(),
        recommendedUser.getIsOnline(),
        "매우 추천",
        recommendedUser.getGrade(),
        recommendedUserSchool == null ? "not registered school" : recommendedUserSchool.getLocation(),
        recommendedUser.getInterest(),
        ar.getSimilarityMessage(),
        "user avartar image url"
    );
    return rrDTO;
  }

  public AIRecommendation getRecommendataionInfo(Integer userId, Integer recommendedUserId) {
    return aiRecommendationRepository.getRecommendataionInfoByUserIdAndRecommendedUserId(userId, recommendedUserId);
  }

  @Transactional
  public AIRecommendation updateRecommendation(Integer recommendationId, AIResponseDTO.RecommendedUser newAIRecommendation) {
    // 기존 추천 정보 조회
    AIRecommendation aiRecommendation = aiRecommendationRepository.findById(recommendationId)
        .orElseThrow(() -> new IllegalStateException("not found recommendation id : " + recommendationId));

    // 새로운 값으로 업데이트
    if (newAIRecommendation.getSenderId() != null) {
      aiRecommendation.setRecommendedUserId(newAIRecommendation.getSenderId());
    }

    if (newAIRecommendation.getSimilarity() != null) {
      aiRecommendation.setSimilarity(newAIRecommendation.getSimilarity());
    }

    if (newAIRecommendation.getMessage() != null) {
      aiRecommendation.setSimilarityMessage(newAIRecommendation.getMessage());
    }

    if (newAIRecommendation.getInterests() != null && !newAIRecommendation.getInterests().isEmpty()) {
      aiRecommendation.setRecommendedInterestList(newAIRecommendation.getInterests());
    }

    // 저장된 변경 내용을 반환
    return aiRecommendationRepository.save(aiRecommendation);
  }

  public boolean isExistRecommendedUser(Integer senderId, Integer recommendedUserId) {
    return aiRecommendationRepository.getRecommendationByUserIdAndRecommendedUserId(
        senderId, recommendedUserId).isPresent();
  }

  public void deleteRecommendationInfo(Integer recommendationInfoId) {
    aiRecommendationRepository.deleteById(recommendationInfoId);
  }
}
