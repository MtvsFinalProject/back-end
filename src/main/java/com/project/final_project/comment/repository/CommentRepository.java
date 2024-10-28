package com.project.final_project.comment.repository;

import com.project.final_project.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentCustomRepository {

}
