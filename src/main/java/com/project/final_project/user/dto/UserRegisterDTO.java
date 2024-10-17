package com.project.final_project.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegisterDTO {

  String socialId;

//  @NotBlank(message = "이름은 필수 항목입니다.")
  String name;

//  @NotNull(message = "나이는 필수 항목입니다.")
Integer age;

//  @NotBlank(message = "학교 이름은 필수 항목입니다.")
  String schoolName;

//  @NotNull(message = "학년은 필수 항목입니다.")
Integer grade;

//  @NotNull(message = "선호 사항은 비어 있을 수 없습니다.")
//  @Size(min = 1, message = "최소 하나 이상의 선호 사항을 입력해야 합니다.")
//  List<String> preferences;

//  @NotNull(message = "생일은 필수 항목입니다.")
//  @Past(message = "생일은 과거 날짜여야 합니다.")
  String birthday;

//  @NotBlank(message = "성별은 필수 항목입니다.")
//  @Pattern(regexp = "^(male|female|other)$", message = "성별은 'male', 'female' 또는 'other'이어야 합니다.")
  String gender;

//  @NotBlank(message = "이메일은 필수 항목입니다.")
//  @Email(message = "유효한 이메일 형식이어야 합니다.")
  String email;

//  @NotBlank(message = "비밀번호는 필수 항목입니다.")
//  @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
  String password;

//  @NotBlank(message = "닉네임은 필수 항목입니다.")
  String nickname;

//  @NotBlank(message = "전화번호는 필수 항목입니다.")
//  @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 10자리 또는 11자리의 숫자여야 합니다.")
  String phone;
}
