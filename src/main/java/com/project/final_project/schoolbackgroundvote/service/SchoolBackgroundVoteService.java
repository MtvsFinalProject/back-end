package com.project.final_project.schoolbackgroundvote.service;

import com.project.final_project.school.domain.School;
import com.project.final_project.school.repository.SchoolRepository;
import com.project.final_project.schoolbackgroundvote.domain.SchoolBackgroundVote;
import com.project.final_project.schoolbackgroundvote.dto.SchoolBackgroundVoteProcessDTO;
import com.project.final_project.schoolbackgroundvote.dto.SchoolBackgroundVoteRequestDTO;
import com.project.final_project.schoolbackgroundvote.repository.SchoolBackgroundColorVoteRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolBackgroundVoteService {

  private final SchoolBackgroundColorVoteRepository voteRepository;

  // @Transactional 애노테이션은 메소드가 트랜잭션 범위 내에서 실행되도록 설정하여,
// 작업이 중간에 실패하면 모든 데이터베이스 작업을 롤백하도록 함.
  @Transactional
  public void processVotes(List<SchoolBackgroundVoteProcessDTO> votes) {

    // votes는 유저들이 투표한 데이터를 담고 있는 리스트이며, 각 항목은 SchoolBackgroundVoteProcessDTO 객체.
    // 스트림 API를 사용하여 votes 리스트를 배경색 ID별로 그룹화하고, 각 배경색이 몇 표를 받았는지 집계.
    Map<Integer, Long> voteCount = votes.stream()
        .collect(Collectors.groupingBy(SchoolBackgroundVoteProcessDTO::getBackgroundColorId, Collectors.counting()));

    // 집계된 결과 중 가장 많이 투표된 배경색을 찾음.
    // voteCount 맵에서 값(투표 수)을 기준으로 가장 큰 값을 가진 항목의 키(배경색 ID)를 가져옴.
    // 만약 투표된 값이 없으면 null을 반환.
    Integer mostVotedBackgroundColorId = voteCount.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);

    // 가장 많이 투표된 배경색이 null이 아닌 경우(즉, 투표 결과가 있을 경우)
    if(mostVotedBackgroundColorId != null){

      // voteRepository에서 가장 많이 투표된 배경색 ID를 기반으로 학교 정보를 찾음.
      // 해당 ID가 존재하지 않으면 예외를 발생시킴 (학교를 찾을 수 없는 경우).
      SchoolBackgroundVote foundSchool =
          voteRepository.findById(mostVotedBackgroundColorId)
              .orElseThrow(() -> new RuntimeException("School not found"));

      // 가장 많이 투표된 배경색을 학교의 배경색으로 설정.
      foundSchool.setBackgroundColorId(mostVotedBackgroundColorId);

      // 이 변경 사항은 @Transactional에 의해 자동으로 커밋되어 데이터베이스에 반영됨.
    }
  }


  public SchoolBackgroundVote vote(SchoolBackgroundVoteRequestDTO requestDTO) {
    SchoolBackgroundVote vote = new SchoolBackgroundVote(requestDTO);
    return voteRepository.save(vote);
  }
}
