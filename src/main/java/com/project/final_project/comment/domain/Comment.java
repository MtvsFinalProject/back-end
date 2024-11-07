package com.project.final_project.comment.domain;

import com.project.final_project.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "comment_content")
  private String content;

  @ColumnDefault("FALSE")
  @Column(nullable = false)
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User writer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_comment_id")
  private Comment parentComment; // null 이면 최상위 댓글

  @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
  private List<Comment> childrenComment = new ArrayList<>();

  private String boardType;
  private Integer boardId;

  @Column(name = "comment_time")
  private String timeStamp;

  @PrePersist
  protected void CreateTime() {
    timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
  }

  public Comment(String content, String boardType, Integer boardId) {
    this.content = content;

  }

  public void updateWriter(User user) {
    this.writer = user;
  }

  public void updateParent(Comment comment) {
    this.parentComment = comment;
  }

  public void changeIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public void updateContent(String content) {
    this.content = content;
  }
}
