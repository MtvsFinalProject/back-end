package com.project.final_project.lockerguestbook.dto;

import com.project.final_project.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LockerGuestBookRegisterDTO {

  private String content; // 쪽지 내용
  private User user; // 쪽지 남긴 유저

}
