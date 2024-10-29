package com.project.final_project.user.domain;

import com.project.final_project.school.domain.School;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
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
