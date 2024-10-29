package com.project.final_project.airecommendation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.final_project.airecommendation.domain.AIRecommendation;
import com.project.final_project.airecommendation.dto.AIResponseDTO;
import com.project.final_project.airecommendation.repository.AIRecommendationRepository;
import com.project.final_project.chatlog.domain.ChatLog;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.batch.item.Chunk;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class AIRecommendationService {

  private final AIRecommendationRepository aiRecommendationRepository;
  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final UserService userService;

  private static final String AI_URL = "http://localhost:8000/recommend";

  // 20개의 ChatLog 메시지를 객체 형태로 추출하여 AI 서버에 전송
  public AIResponseDTO sendToAI(Chunk<? extends ChatLog> chatLogs, Long senderId) {

    Integer isenderId = senderId.intValue();

    // 메시지를 객체 형태로 추출
    List<Map<String, String>> messages = chatLogs != null && !chatLogs.isEmpty() ?
        chatLogs.getItems().stream()
            .map(chatLog -> Map.of("message", chatLog.getMessage()))
            .collect(Collectors.toList())
        : null;

    // AI 서버에 보낼 요청 바디 생성
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("senderId", isenderId);  // senderId 설정
    requestBody.put("messages", messages);  // 메시지 리스트 전송

    // 요청 바디가 null인지 확인하는 방어 로직 추가
    if (requestBody.get("messages") == null || messages.isEmpty()) {
      log.error("메시지 데이터가 없습니다.");
      throw new IllegalArgumentException("메시지 데이터가 존재하지 않습니다.");
    }

    // RestTemplate를 사용하여 동기식 요청 수행
    String responseBody = restTemplate.postForObject(AI_URL, requestBody, String.class);

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

  public boolean isExistBySenderIdAndFriendId(Integer senderId, Integer friendId) {
    return aiRecommendationRepository.existsByUserIdAndRecommendedFriendId(senderId, friendId);
  }

  public void saveRecommendation(AIRecommendation recommendation) {
    System.out.println("recommendations = " + recommendation);
    aiRecommendationRepository.save(recommendation);
  }

  public List<Integer> getRecommendedFriendIds(Integer userId) {
    AIRecommendation aiRecommendation = aiRecommendationRepository.getRecommendationByUserId(userId)
        .orElseThrow(
            () -> new IllegalStateException("not found recommendation userId : " + userId));
    return aiRecommendation.getRecommendedFriendIds();
  }

  public List<String> getRecommendedFriendNames(Integer userId) {
    AIRecommendation aiRecommendation = aiRecommendationRepository.getRecommendationByUserId(userId)
        .orElseThrow(() -> new IllegalStateException("not found recommendation userId : " + userId));

    return aiRecommendation.getRecommendedFriendIds().stream()
        .map(id -> {
          User foundUser = userService.getUser(id);
          return foundUser.getName();
        })
        .collect(Collectors.toList());
  }

  public AIRecommendation getRecommendByUserId(Integer senderId) {
    return aiRecommendationRepository.getRecommendationByUserId(senderId).orElse(null);
  }
}
