package com.project.final_project.comment.service;

import com.project.final_project.board.domain.Board;
import com.project.final_project.board.repository.BoardRepository;
import com.project.final_project.comment.domain.Comment;
import com.project.final_project.comment.dto.CommentRequestDTO;
import com.project.final_project.comment.mapper.CommentRequestMapper;
import com.project.final_project.comment.repository.CommentRepository;
import com.project.final_project.common.exception.NotFoundException;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final CommentRequestMapper commentRequestMapper;

  public Comment insertComment(Integer boardId, CommentRequestDTO commentRequestDTO) {

    User user = userRepository.findById(commentRequestDTO.getUserId())
        .orElseThrow(() -> new NotFoundException("User not found id : " + commentRequestDTO.getUserId()));

    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new NotFoundException("User not found id : " + boardId));

    Comment comment = commentRequestMapper.toEntity(commentRequestDTO);

    Comment parentComment;
    if(commentRequestDTO.getParentCommentId() != null) {
      parentComment = commentRepository.findById(commentRequestDTO.getParentCommentId())
          .orElseThrow(() -> new NotFoundException("parentComment not found id : " + commentRequestDTO.getParentCommentId()));

      comment.updateParent(parentComment);
    }

    comment.updateWriter(user);
    comment.updateBoard(board);

    return commentRepository.save(comment);
  }

  @Transactional
  public void delete(Integer commentId) {
    Comment comment = commentRepository.findCommentByIdWithParent(commentId)
        .orElseThrow(() -> new NotFoundException("comment not found id : " + commentId));

    if(comment.getChildrenComment().size() != 0){ // 자식이 있으면 상태만 변경
      comment.changeIsDeleted(true);
    } else {
      commentRepository.delete(getDeletableAncestorComment(comment));
    }
  }

  private Comment getDeletableAncestorComment(Comment comment) {
    Comment parentComment = comment.getParentComment(); // 현재 댓글의 부모를 구함
    // 부모가 있고, 부모의 자식이 1개(지금 삭제하는 댓글)이고, 부모의 삭제 상태가 TRUE인 댓글이라면 재귀
    if(parentComment != null && parentComment.getChildrenComment().size() == 1 && parentComment.getIsDeleted()){
      return getDeletableAncestorComment(parentComment);
    }
    return comment; // 삭제해야하는 댓글 반환
  }

  @Transactional
  public void update(Integer commentId, CommentRequestDTO commentRequestDTO) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException("comment not found id " + commentId));
    
    //TODO 해당 메서드를 호출하는 사용자와 댓글을 작성한 작성자가 같은지 확인하는 로직이 필요함.
    comment.updateContent(commentRequestDTO.getContent());
  }
}
