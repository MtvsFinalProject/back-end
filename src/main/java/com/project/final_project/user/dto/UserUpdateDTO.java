package com.project.final_project.user.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateDTO {

  Integer userId;
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
}
