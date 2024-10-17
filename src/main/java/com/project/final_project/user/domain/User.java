package com.project.final_project.user.domain;

import com.project.final_project.school.domain.School;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "user")
@Builder
@Getter
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue
  Integer id;

  @Column(name = "social_id")
  String socialId; // 소셜 로그인에서 제공한 고유 ID 저장

  @Column(name = "user_name")
  String name;

  @Column(name = "user_age")
  Integer age;

  @Column(name = "user_grade")
  Integer grade;

  @Column(name = "user_birthday")
  String birthday;

  @Column(name = "user_gender")
  String gender;

  @Column(name = "user_email")
  String email;

  @Column(name = "user_password")
  String password;

  @Column(name = "user_nickname")
  String nickname;

  @Column(name = "user_phone")
  String phone;

  @Column(name = "user_level")
  Integer level;

  @Column(name = "user_exp")
  Integer exp;

  @Column(name = "user_max_exp")
  Integer maxExp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  School school;

  //==생성 메소드==//
  public static User createUser(UserRegisterDTO dto){
       return User.builder()
        .socialId(dto.getSocialId())
        .name(dto.getName())
        .age(dto.getAge())
        .grade(dto.getGrade())
//        .preferences(dto.getPreferences())
        .birthday(dto.getBirthday())
        .gender(dto.getGender())
        .email(dto.getEmail())
        .password(dto.getPassword())
        .nickname(dto.getNickname())
        .phone(dto.getPhone())
        .level(1)
        .exp(0)
        .maxExp(100)
        .build();
  }

  //==수정 메소드==//
  public User updateUser(UserUpdateDTO dto) {
    if (dto.getName() != null) {
      this.name = dto.getName();
    }
    if (dto.getAge() != null) {
      this.age = dto.getAge();
    }
    if (dto.getGrade() != null) {
      this.grade = dto.getGrade();
    }
    if (dto.getBirthday() != null) {
      this.birthday = dto.getBirthday();
    }
    if (dto.getGender() != null) {
      this.gender = dto.getGender();
    }
    if (dto.getNickname() != null) {
      this.nickname = dto.getNickname();
    }
    if (dto.getPhone() != null) {
      this.phone = dto.getPhone();
    }
//    if (dto.getPreferences() != null) {
//      this.preferences = dto.getPreferences();
//    }
    return this;
  }


  //==비즈니스 로직==//
  public void gainExp(Integer exp) {
    this.exp += exp;
    while(this.exp >= this.maxExp){
      levelUp();
    }
  }

  private void levelUp() {
    this.exp -= this.maxExp;
    this.level += 1;
    this.maxExp = calculateMaxExpForNextLevel(this.level);
  }

  private Integer calculateMaxExpForNextLevel(Integer level) {
    return 100 + (level - 1) * 50;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", socialId='" + socialId + '\'' +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", grade=" + grade +
//        ", preferences=" + preferences +
        ", birthday='" + birthday + '\'' +
        ", gender='" + gender + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", nickname='" + nickname + '\'' +
        ", phone='" + phone + '\'' +
        ", level=" + level +
        ", exp=" + exp +
        ", maxExp=" + maxExp +
        ", school=" + school +
        '}';
  }
}
