package com.project.final_project.friendship.dto;

import com.project.final_project.school.domain.School;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetFriendResponseDTO {
  String name;
  Integer age;
  Integer grade;
  String birthday;
  String gender;
  String email;
  String nickname;
  String phone;
  Integer level;
  Integer exp;
  Integer maxExp;
  School school;
}
