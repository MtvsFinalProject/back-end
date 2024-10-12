package com.project.final_project.login.kakao.repository;


import com.project.final_project.login.kakao.domain.KakaoLoginLog;
import com.project.final_project.login.kakao.dto.KakaoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KakaoLoginLogRepository extends JpaRepository<KakaoLoginLog, Long> {
  @Query("select k from KakaoLoginLog k where k.state = :state")
  KakaoLoginLog getKakaoLoginLogByState(@Param("state") String state);
}
