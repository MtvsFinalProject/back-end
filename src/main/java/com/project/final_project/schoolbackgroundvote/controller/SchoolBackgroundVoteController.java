package com.project.final_project.schoolbackgroundvote.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import com.project.final_project.schoolbackgroundvote.domain.SchoolBackgroundVote;
import com.project.final_project.schoolbackgroundvote.dto.SchoolBackgroundVoteProcessDTO;
import com.project.final_project.schoolbackgroundvote.dto.SchoolBackgroundVoteRequestDTO;
import com.project.final_project.schoolbackgroundvote.dto.SchoolBackgroundVoteResponseDTO;
import com.project.final_project.schoolbackgroundvote.service.SchoolBackgroundVoteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school-background-vote")
@RequiredArgsConstructor
public class SchoolBackgroundVoteController {

  private final SchoolBackgroundVoteService schoolBackgroundVoteService;

  @PostMapping
  public ResponseResult<SchoolBackgroundVoteResponseDTO> vote(@RequestBody SchoolBackgroundVoteRequestDTO requestDTO) {
    SchoolBackgroundVote vote = schoolBackgroundVoteService.vote(requestDTO);
    return success();
  }


  @PostMapping("/process")
  public ResponseResult<?> processVotes(@RequestBody List<SchoolBackgroundVoteProcessDTO> votes) {
    schoolBackgroundVoteService.processVotes(votes);
    return success();
  }

}
