package com.project.final_project.friendship.dto;

import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FriendshipResponseDTO {

  private Integer id;
  private UserDTO requester;
  private UserDTO receiver;
  private boolean isAccepted;

  public FriendshipResponseDTO(Integer id, UserDTO requester, UserDTO receiver, boolean isAccepted) {
    this.id = id;
    this.requester = requester;
    this.receiver = receiver;
    this.isAccepted = isAccepted;
  }
}
