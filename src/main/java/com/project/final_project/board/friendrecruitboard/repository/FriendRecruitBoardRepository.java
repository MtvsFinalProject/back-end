package com.project.final_project.board.friendrecruitboard.repository;

import com.project.final_project.board.friendrecruitboard.domain.FriendRecruitBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRecruitBoardRepository extends JpaRepository<FriendRecruitBoard, Integer> {

}
