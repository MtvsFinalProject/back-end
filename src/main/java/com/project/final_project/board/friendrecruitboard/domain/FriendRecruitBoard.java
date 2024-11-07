package com.project.final_project.board.friendrecruitboard.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "friend_recruit_board")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRecruitBoard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;




}
