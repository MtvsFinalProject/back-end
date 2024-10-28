package com.project.final_project.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {

  private Integer userId;
  private Integer parentCommentId;
  private String content;

  public CommentRequestDTO(String content) {
    this.content = content;
  }

}
