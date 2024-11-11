package com.project.final_project.user.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterDTO {

  String socialId;
  String name;
  Integer grade;
  String birthday;
  Boolean gender;
  String email;
  String password;
  String phone;
  String statusMessage;
  Integer gold;
  List<String> interest = new ArrayList<>();
  Integer schoolId;

  public UserRegisterDTO(String socialId, String name, Integer grade, String birthday,
      Boolean gender, String email, String password, String phone, String statusMessage) {
    this.socialId = socialId;
    this.name = name;
    this.grade = grade;
    this.birthday = birthday;
    this.gender = gender;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.statusMessage = statusMessage;
  }

  public UserRegisterDTO(String socialId, String name, Integer grade, String birthday,
      Boolean gender, String email, String password, String phone,
      Integer schoolId) {
    this.socialId = socialId;
    this.name = name;
    this.grade = grade;
    this.birthday = birthday;
    this.gender = gender;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.schoolId = schoolId;
  }
}
