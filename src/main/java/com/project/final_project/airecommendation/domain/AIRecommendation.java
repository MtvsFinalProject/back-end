package com.project.final_project.airecommendation.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

@Entity
@Table(name = "ai_recommend")
@Setter
public class AIRecommendation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_id")
  private Integer userId;

  // 추천된 친구들의 ID 목록
  @ElementCollection
  @CollectionTable(name = "recommended_friend_ids",
                  joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
  private List<Integer> recommendedFriendIds = new ArrayList<>();  // 빈 리스트로 초기화

  // Getters and Setters
  public Integer getSenderId() {
    return userId;
  }

  public List<Integer> getRecommendedFriendIds() {
    if (recommendedFriendIds == null) {
      recommendedFriendIds = new ArrayList<>();  // Null일 경우 빈 리스트로 초기화
    }
    return recommendedFriendIds;
  }

  @Override
  public String toString() {
    return "AIRecommendation{" +
        "id=" + id +
        ", userId=" + userId +
        ", recommendedFriendIds=" + recommendedFriendIds +
        '}';
  }
}
