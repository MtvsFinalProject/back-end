package com.project.final_project.schoolbackgroundvote.dto;

import com.project.final_project.schoolbackgroundvote.domain.SchoolBackgroundVote;
import lombok.Getter;

@Getter
public class SchoolBackgroundVoteResponseDTO {

  Integer backgroundColorId;

  public SchoolBackgroundVoteResponseDTO(SchoolBackgroundVote vote) {
    this.backgroundColorId = vote.getBackgroundColorId();
  }
}
