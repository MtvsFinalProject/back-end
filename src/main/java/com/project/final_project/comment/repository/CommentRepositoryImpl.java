package com.project.final_project.comment.repository;

import static com.project.final_project.comment.domain.QComment.comment;

import com.project.final_project.comment.domain.Comment;
import com.project.final_project.comment.dto.CommentRequestDTO;
import com.project.final_project.comment.dto.CommentResponseDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentCustomRepository{

  private final JPAQueryFactory queryFactory;

  @Override
  public List<CommentResponseDTO> findByBoardId(Integer id) {

    List<Comment> comments = queryFactory.selectFrom(comment)
        .leftJoin(comment.parentComment).fetchJoin()
        .where(comment.boardId.eq(id))
        .orderBy(comment.parentComment.id.asc().nullsFirst(),
            comment.timeStamp.asc())
        .fetch();

    List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
    Map<Integer, CommentResponseDTO> commentDTOHashMap = new HashMap<>();

    comments.forEach(c -> {
      CommentResponseDTO commentResponseDTO = CommentResponseDTO.convertCommentToDTO(c);
      commentDTOHashMap.put(commentResponseDTO.getId(), commentResponseDTO);
      if(c.getParentComment() != null) commentDTOHashMap.get(c.getParentComment().getId()).getChildren().add(commentResponseDTO);
      else commentResponseDTOList.add(commentResponseDTO);
    });

    return commentResponseDTOList;
  }

  @Override
  public Optional<Comment> findCommentByIdWithParent(Integer id) {

    Comment selectedComment = queryFactory.select(comment)
        .from(comment)
        .leftJoin(comment.parentComment).fetchJoin()
        .where(comment.id.eq(id))
        .fetchOne();

    return Optional.ofNullable(selectedComment);
  }
}
