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
  private String boardType;
  private Integer boardId;

  public CommentRequestDTO(String content, String boardType, Integer boardId) {
    this.content = content;
    this.boardType = boardType;
    this.boardId = boardId;
  }

}
