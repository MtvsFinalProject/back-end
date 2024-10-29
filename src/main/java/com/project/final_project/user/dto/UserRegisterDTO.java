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
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class UserRegisterDTO {

  String socialId;
  String name;
  Integer age;
  Integer grade;
  String birthday;
  String gender;
  String email;
  String password;
  String nickname;
  String phone;
  Integer schoolId;

  public UserRegisterDTO(String socialId, String name, Integer age, Integer grade, String birthday,
      String gender, String email, String password, String nickname, String phone) {
    this.socialId = socialId;
    this.name = name;
    this.age = age;
    this.grade = grade;
    this.birthday = birthday;
    this.gender = gender;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.phone = phone;
  }

  public UserRegisterDTO(String socialId, String name, Integer age, Integer grade, String birthday,
      String gender, String email, String password, String nickname, String phone,
      Integer schoolId) {
    this.socialId = socialId;
    this.name = name;
    this.age = age;
    this.grade = grade;
    this.birthday = birthday;
    this.gender = gender;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.phone = phone;
    this.schoolId = schoolId;
  }
}
