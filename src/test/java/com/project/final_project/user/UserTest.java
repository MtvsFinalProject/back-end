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
//    // 더미 유저 1
//    userService.registerUser(new UserRegisterDTO(
//        "socialId1", "John Doe", 30L, "Harvard", 3L,
//        Arrays.asList("music", "sports"), "1990-01-01",
//        "male", "john@example.com", "password123", "johndoe", "01012345678"
//    ));
//
//    // 더미 유저 2
//    userService.registerUser(new UserRegisterDTO(
//        "socialId2", "Jane Smith", 25L, "Stanford", 4L,
//        Arrays.asList("art", "reading"), "1995-03-10",
//        "female", "jane@example.com", "password456", "janesmith", "01098765432"
//    ));
//
//    // 더미 유저 3
//    userService.registerUser(new UserRegisterDTO(
//        "socialId3", "Mike Johnson", 28L, "MIT", 2L,
//        Arrays.asList("gaming", "programming"), "1992-06-15",
//        "male", "mike@example.com", "password789", "mikejohnson", "01011112222"
//    ));

    // 더미 유저 1
    UserRegisterDTO user1 = UserRegisterDTO.builder()
        .socialId("socialId1")
        .name("John Doe")
        .age(30)
        .schoolName("Harvard")
        .grade(3)
//        .preferences(Arrays.asList("music", "sports"))
        .birthday("1990-01-01")
        .gender("male")
        .email("john@example.com")
        .password("password123")
        .nickname("johndoe")
        .phone("01012345678")
        .build();

    // 더미 유저 2
    UserRegisterDTO user2 = UserRegisterDTO.builder()
        .socialId("socialId2")
        .name("Jane Smith")
        .age(25)
        .schoolName("Stanford")
        .grade(4)
//        .preferences(Arrays.asList("art", "reading"))
        .birthday("1995-03-10")
        .gender("female")
        .email("jane@example.com")
        .password("password456")
        .nickname("janesmith")
        .phone("01098765432")
        .build();

    // 더미 유저 3
    UserRegisterDTO user3 = UserRegisterDTO.builder()
        .socialId("socialId3")
        .name("Mike Johnson")
        .age(28)
        .schoolName("MIT")
        .grade(2)
//        .preferences(Arrays.asList("gaming", "programming"))
        .birthday("1992-06-15")
        .gender("male")
        .email("mike@example.com")
        .password("password789")
        .nickname("mikejohnson")
        .phone("01011112222")
        .build();

    // 유저 등록
//    userService.registerUser(user1);
//    userService.registerUser(user2);
//    userService.registerUser(user3);

  }

  @DisplayName("아이디로 유저 조회 테스트")
  @Test
  void findUserTest() {
    Integer tmp = userService.getAllUser().get(0).getId();
    User foundUser = userService.getUser(tmp);
    Assertions.assertThat(foundUser.getName()).isEqualTo("John Doe");
  }

  @DisplayName("유저 레벨업 테스트")
  @Test
  @Transactional
  void levelUpUserTest() {
    Integer tmp = userService.getAllUser().get(0).getId();
    userService.gainExp(tmp, 100);
    User foundUser1 = userService.getUser(tmp);
    Assertions.assertThat(foundUser1.getLevel()).isEqualTo(2);
  }

  @DisplayName("유저 수정 테스트")
  @Test
  @Transactional
  void updateUserTest() {
    Integer tmp = userService.getAllUser().get(0).getId();
    UserUpdateDTO dto = UserUpdateDTO.builder()
            .userId(tmp)
            .name("John Doe")
            .age(30)
            .schoolName("Harvard")
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
