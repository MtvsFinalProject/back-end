package com.project.final_project.school.dto;

import com.project.final_project.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolRegisterDTO {
  String schoolName;
  String location;
  List<User> userList = new ArrayList<>();

  public SchoolRegisterDTO(String schoolName, String location) {
    this.schoolName = schoolName;
    this.location = location;
  }

  public SchoolRegisterDTO(String schoolName, String location, List<User> userList) {
    this.schoolName = schoolName;
    this.location = location;
    this.userList = userList;
  }
}
