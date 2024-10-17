package com.project.final_project.user.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateDTO {

  Integer userId;
  String name;
  Integer age;
  String schoolName;
  Integer grade;
//  List<String> preferences;
  String birthday;
  String gender;
  String email;
  String password;
  String nickname;
  String phone;

}
