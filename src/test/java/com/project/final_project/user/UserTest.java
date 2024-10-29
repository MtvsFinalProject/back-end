package com.project.final_project.user;

import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import com.project.final_project.user.repository.UserRepository;
import com.project.final_project.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @BeforeEach
  void beforeEach() {

  }

  @DisplayName("아이디로 유저 조회 테스트")
  @Test
  void findUserTest() {
    Integer tmp = userService.getAllUser().get(0).getUserId();
    User foundUser = userService.getUser(tmp);
    Assertions.assertThat(foundUser.getName()).isEqualTo("John Doe");
  }

  @DisplayName("유저 레벨업 테스트")
  @Test
  @Transactional
  void levelUpUserTest() {
    Integer tmp = userService.getAllUser().get(0).getUserId();
    userService.gainExp(tmp, 100);
    User foundUser1 = userService.getUser(tmp);
    Assertions.assertThat(foundUser1.getLevel()).isEqualTo(2);
  }

  @DisplayName("유저 수정 테스트")
  @Test
  @Transactional
  void updateUserTest() {
    Integer tmp = userService.getAllUser().get(0).getUserId();
    UserUpdateDTO dto = UserUpdateDTO.builder()
            .userId(tmp)
            .name("John Doe")
            .age(30)
            .grade(3)
            .birthday("1990-01-01")
            .gender("male")
            .email("john@example.com")
            .password("password123")
            .nickname("newNickname")
            .phone("01012345678")
            .build();
    userService.updateUser(dto);
    User foundUser1 = userService.getUser(tmp);
    Assertions.assertThat(foundUser1.getNickname()).isEqualTo("newNickname");
  }
}
