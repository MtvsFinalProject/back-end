package com.project.final_project.comment.repository;

import com.project.final_project.comment.domain.Comment;
import com.project.final_project.comment.dto.CommentResponseDTO;
import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {

  List<CommentResponseDTO> findByBoardId(Integer id);

  Optional<Comment> findCommentByIdWithParent(Integer id);

}
