package com.project.final_project.login.kakao.controller;

import com.project.final_project.login.common.MsgEntity;
import com.project.final_project.login.kakao.dto.KakaoDTO;
import com.project.final_project.login.kakao.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {

  private final KakaoService kakaoService;

  @GetMapping("/login/oauth2/code/kakao")
  public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
    KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

    return ResponseEntity.ok()
        .body(new MsgEntity("Success", kakaoInfo));
  }

}
