package com.project.final_project.comment.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.comment.domain.Comment;
import com.project.final_project.comment.dto.CommentRequestDTO;
import com.project.final_project.comment.dto.CommentResponseDTO;
import com.project.final_project.comment.service.CommentService;
import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseResult<Comment> insert(@RequestParam("boardId") Integer boardId, @RequestBody
      CommentRequestDTO commentRequestDTO) {
    
    //TODO 뭘 return 하는게 좋을지 고민해보자
    Comment comment = commentService.insertComment(commentRequestDTO);
    return success(comment);
  }

  @GetMapping
  public ResponseResult<List<CommentResponseDTO>> getComments(
      @RequestParam("boardType") String boardType,
      @RequestParam("boardId") Integer boardId
  ) {
    List<CommentResponseDTO> comments = commentService.findCommentsByBoard(boardType, boardId);
    return success(comments);
  }

  @PatchMapping
  public ResponseResult<Comment> update(
      @RequestParam("commentId") Integer commentId, @RequestBody CommentRequestDTO commentRequestDTO)  {
    commentService.update(commentId, commentRequestDTO);

    return success();
  }

  @DeleteMapping
  public ResponseResult<Comment> delete(@RequestParam("commentId") Integer commentId) {
    commentService.delete(commentId);

    return success();
  }

}
