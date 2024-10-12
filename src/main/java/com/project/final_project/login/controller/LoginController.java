package com.project.final_project.login.controller;

import com.project.final_project.login.kakao.domain.KakaoLoginLog;
import com.project.final_project.login.kakao.service.KakaoLoginLogService;
import com.project.final_project.login.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
  private final KakaoService kakaoService;
  private final KakaoLoginLogService kakaoLoginLogService;

  @PostMapping("/logout")
  @ResponseBody
  public ResponseEntity<String> kakaoLogout(@RequestHeader("state") String state) {
    try {
      KakaoLoginLog log = kakaoLoginLogService.getKakaoLoginLogByState(state);
      String result = kakaoService.logout(log.getAccessToken());
      System.out.println(result);
      return ResponseEntity.ok(result);  // 로그아웃 성공 메시지 반환
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to logout: " + e.getMessage());
    }
  }

  @PostMapping("/unlink")
  @ResponseBody
  public ResponseEntity<String> kakaoUnlink(@RequestHeader("Authorization") String accessToken) {
    try {
      String result = kakaoService.unlink(accessToken);
      System.out.println(result);
      return ResponseEntity.ok(result);  // 로그아웃 성공 메시지 반환
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to logout: " + e.getMessage());
    }
  }
}
