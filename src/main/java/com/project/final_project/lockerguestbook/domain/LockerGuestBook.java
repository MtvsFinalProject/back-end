package com.project.final_project.lockerguestbook.domain;

import com.project.final_project.lockerguestbook.dto.LockerGuestBookRegisterDTO;
import com.project.final_project.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locker_guest_book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LockerGuestBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "content")
  private String content; // 쪽지 내용

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user; // 쪽지 남긴 유저


  public LockerGuestBook(Integer id, String content, User user) {
    this.id = id;
    this.content = content;
    this.user = user;
  }

  public LockerGuestBook(String content, User user) {
    this.content = content;
    this.user = user;
  }

  public static LockerGuestBook createLockerGuestBook(LockerGuestBookRegisterDTO dto){
    LockerGuestBook lockerGuestBook = new LockerGuestBook();
    lockerGuestBook.setContent(dto.getContent());
    lockerGuestBook.setUser(dto.getUser());
    return lockerGuestBook;
  }
}
