package com.project.final_project.airecommendation.repository;

import com.project.final_project.airecommendation.domain.AIRecommendation;
import java.util.Optional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AIRecommendationRepository extends JpaRepository<AIRecommendation, Integer> {

  // userId로 유저를 찾고, 그 유저의 추천 친구 목록에 특정 friendId가 존재하는지 확인
  @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END " +
      "FROM AIRecommendation a " +
      "WHERE a.userId = :userId AND :friendId IN elements(a.recommendedFriendIds)")
  boolean existsByUserIdAndRecommendedFriendId(@Param("userId") Integer userId, @Param("friendId") Integer friendId);


  @Query("SELECT r FROM AIRecommendation r WHERE r.userId = :userId")
  Optional<AIRecommendation> getRecommendationByUserId(@Param("userId") Integer userId);

}
