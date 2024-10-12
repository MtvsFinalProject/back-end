package com.project.final_project.login.kakao.controller;

import com.project.final_project.login.kakao.domain.KakaoLoginLog;
import com.project.final_project.login.kakao.service.KakaoLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kakaoLoginLog")
@RequiredArgsConstructor
public class KakaoLoginLogController {

  private final KakaoLoginLogService kakaoLoginLogService;

  @GetMapping
  ResponseEntity<KakaoLoginLog> getKakaoLoginLog(@RequestParam("state") String state) {
    return ResponseEntity.ok(kakaoLoginLogService.getKakaoLoginLogByState(state));
  }
}
