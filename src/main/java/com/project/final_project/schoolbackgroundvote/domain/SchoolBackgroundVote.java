package com.project.final_project.schoolbackgroundvote.domain;

import com.project.final_project.schoolbackgroundvote.dto.SchoolBackgroundVoteRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shool_background_vote")
@Data
public class SchoolBackgroundVote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "background_color_id")
  private Integer backgroundColorId;

  @Column(name = "user_id")
  private Integer userId;

  protected SchoolBackgroundVote() {}

  public SchoolBackgroundVote(Integer backgroundColorId, Integer userId) {
    this.backgroundColorId = backgroundColorId;
    this.userId = userId;
  }

  public SchoolBackgroundVote(SchoolBackgroundVoteRequestDTO dto) {
    this.backgroundColorId = dto.getBackgroundColorId();
    this.userId = dto.getUserId();
  }

}
