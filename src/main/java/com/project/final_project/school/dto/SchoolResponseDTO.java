package com.project.final_project.school.dto;

import com.project.final_project.school.domain.School;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// UserDTO에서 순환참조 문제 해결을 위해 만듬
public class SchoolResponseDTO {

  Integer id;
  String schoolName;
  String location;

  public SchoolResponseDTO(School school) {
    if(school != null) {
      this.id = school.getId();
      this.schoolName = school.getSchoolName();
      this.location = school.getLocation();
    }
  }
}
