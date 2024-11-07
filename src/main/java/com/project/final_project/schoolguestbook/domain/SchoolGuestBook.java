package com.project.final_project.schoolguestbook.domain;

import com.project.final_project.school.domain.School;
import com.project.final_project.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "school_guest_book")
@Getter @Setter
public class SchoolGuestBook {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "school_id")
  private Integer schoolId;

  @Column(name = "school_guest_book_content")
  private String content;

  @Column(name = "school_guest_book_rgb")
  private String rgb;

  @Column(name = "school_guest_book_regist_date")
  private String registDate;

  @Column(name = "school_guest_book_like_count")
  private Integer likeCount;

  protected SchoolGuestBook() {}

  public SchoolGuestBook(User user, School school, String content, String rgb) {
    this.user = user;
    this.content = content;
    this.rgb = rgb;
  }
}
