package com.project.final_project.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.final_project.school.domain.School;
import com.project.final_project.school.dto.SchoolDTO;
import com.project.final_project.school.dto.SchoolRegisterDTO;
import com.project.final_project.school.dto.SchoolResponseDTO;
import com.project.final_project.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  Integer id;
  String socialId;
  String name;
  Integer grade;
  String birthday;
  Boolean gender;
  String email;
  String password;
  String phone;
  String statusMesasge;
  Integer gold;
  List<String> interest = new ArrayList<>();
  Boolean isOnline;

  SchoolResponseDTO school;

  public UserDTO(User user) {
    this.id = user.getId();
    this.socialId = user.getSocialId();
    this.name = user.getName();
    this.grade = user.getGrade();
    this.birthday = user.getBirthday();
    this.gender = user.getGender();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.phone = user.getPhone();
    this.interest = user.getInterest();
    this.statusMesasge = user.getStatusMessage();
    this.gold = user.getGold();
    this.isOnline = user.getIsOnline();
    this.school = new SchoolResponseDTO(user.getSchool());
  }
}
