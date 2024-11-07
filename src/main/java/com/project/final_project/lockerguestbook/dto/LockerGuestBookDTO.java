package com.project.final_project.lockerguestbook.dto;

import com.project.final_project.lockerguestbook.domain.LockerGuestBook;
import com.project.final_project.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LockerGuestBookDTO {

  private String content; // 쪽지 내용
  private User user; // 쪽지 남긴 유저

  public LockerGuestBookDTO(LockerGuestBook lgb) {
    this.content = lgb.getContent();
    this.user = lgb.getUser();
  }
}
