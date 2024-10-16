package com.project.final_project.login.kakao.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoDTO {

  private Integer id;
  private String email;
  private String nickname;

}
