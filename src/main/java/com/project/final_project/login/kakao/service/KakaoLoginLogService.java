package com.project.final_project.login.kakao.service;

import com.project.final_project.login.kakao.domain.KakaoLoginLog;
import com.project.final_project.login.kakao.repository.KakaoLoginLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoLoginLogService {

  private final KakaoLoginLogRepository kakaoLoginLogRepository;

  public KakaoLoginLog getKakaoLoginLogByState(String state) {
    return kakaoLoginLogRepository.getKakaoLoginLogByState(state);
  }

}
