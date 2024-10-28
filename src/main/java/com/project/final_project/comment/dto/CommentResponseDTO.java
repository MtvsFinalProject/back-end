package com.project.final_project.comment.dto;

import com.project.final_project.comment.domain.Comment;
import com.project.final_project.user.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDTO {

  private Integer id;
  private String content;
  private UserDTO writer;
  private List<CommentResponseDTO> children = new ArrayList<>();

  public CommentResponseDTO(Integer id, String content, UserDTO writer) {
    this.id = id;
    this.content = content;
    this.writer = writer;
  }

  public static CommentResponseDTO convertCommentToDTO(Comment comment) {
    return comment.getIsDeleted() ?
        new CommentResponseDTO(comment.getId(), "삭제된 댓글입니다.", null) :
        new CommentResponseDTO(comment.getId(), comment.getContent(), new UserDTO(comment.getWriter()));
  }

}
