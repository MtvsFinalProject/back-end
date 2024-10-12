package com.project.final_project.login.kakao.controller;

import com.project.final_project.login.kakao.dto.KakaoDTO;
import com.project.final_project.login.kakao.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class KakaoController {

  private final KakaoService kakaoService;

  @GetMapping("/login/oauth2/code/kakao")
  public String addKakaoInfo(HttpServletRequest request) throws Exception {
    KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"), request.getParameter("state"));

    return "login-success";
  }
}



