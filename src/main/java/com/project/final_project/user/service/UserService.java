package com.project.final_project.user.service;

import com.project.final_project.airecommendation.dto.UserRecomendByInterestRequestDTO;
import com.project.final_project.airecommendation.service.AIRecommendationService;
import com.project.final_project.school.domain.School;
import com.project.final_project.school.repository.SchoolRepository;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.dto.UserProfileDTO;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserRegisterSchoolDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import com.project.final_project.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final SchoolRepository schoolRepository;
  private final AIRecommendationService aiRecommendationService;

  public User getUser(Integer id) {
    return userRepository.findById(id).orElse(null);
  }

  public List<UserDTO> getAllUser() {
    return userRepository.findAll().stream()
        .map(UserDTO::new)
        .collect(Collectors.toList());
  }

  public UserDTO registerUser(UserRegisterDTO dto){
    User newUser = createUser(dto);
    User savedUser = userRepository.save(newUser);
    if(savedUser.getInterest() != null && !savedUser.getInterest().isEmpty()) {
      aiRecommendationService.sendInterestToAI(new UserRecomendByInterestRequestDTO(savedUser));
    }
    return new UserDTO(savedUser);
  }

  @Transactional
  public UserDTO updateUser(UserUpdateDTO dto) {
    User foundUser = userRepository.findById(dto.getId()).orElseThrow(
        () -> new IllegalStateException("not found user id:" + dto.getId())
    );

    if(dto.getSchoolId() != null){
      foundUser.setSocialId(dto.getSocialId());
    }
    if (dto.getName() != null) {
      foundUser.setName(dto.getName());
    }
    if (dto.getGrade() != null) {
      foundUser.setGrade(dto.getGrade());
    }
    if (dto.getBirthday() != null) {
      foundUser.setBirthday(dto.getBirthday());
    }
    if (dto.getGender() != null) {
      foundUser.setGender(dto.getGender());
    }
    if (dto.getPhone() != null) {
      foundUser.setPhone(dto.getPhone());
    }
    if(dto.getInterest() != null){
      foundUser.setInterest(new ArrayList<>(dto.getInterest()));
      aiRecommendationService.sendInterestToAI(new UserRecomendByInterestRequestDTO(foundUser));
    }
    if(dto.getStatusMesasge() != null) {
      foundUser.setStatusMessage(dto.getStatusMesasge());
    }
    if(dto.getGold() != null) {
      foundUser.setGold(dto.getGold());
    }
    if(dto.getSchoolId() != null){
      School school = schoolRepository.findById(dto.getSchoolId()).orElseThrow(() -> new IllegalStateException("not found school id:" + dto.getSchoolId()));
      foundUser.setSchool(school);
    }

    return new UserDTO(foundUser);
  }

  @Transactional
  public void removeUser(Integer id) {
    userRepository.deleteById(id);
  }

  public void gainExp(Integer id, Integer exp) {
    User foundUser = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("not found user id:" + id));
    foundUser.gainExp(exp);
    userRepository.save(foundUser);
  }

  public boolean existUserDatas() {
    return userRepository.count() > 0;
  }

  public User createUser(UserRegisterDTO dto) {
    User user = new User();
    user.setSocialId(dto.getSocialId());
    user.setName(dto.getName());
    user.setGrade(dto.getGrade());
    user.setBirthday(dto.getBirthday());
    user.setGender(dto.getGender());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setPhone(dto.getPhone());
    user.setInterest(new ArrayList<>(dto.getInterest()));
    user.setGold(100000);

    user.setLevel(1);
    user.setExp(0);
    user.setMaxExp(100);

    // SchoolService를 사용해 schoolId로 School 객체를 조회
    if(dto.getSchoolId() != null) {
      School school = schoolRepository.findById(dto.getSchoolId()).orElse(null);
      user.setSchool(school); // 조회한 School 객체를 User에 설정
    }

    return userRepository.save(user); // 저장
  }

  @Transactional
  public UserDTO registerSchoolToUser(UserRegisterSchoolDTO dto) {
    User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalStateException("not found registerSchoolForUser id:" + dto.getUserId()));
    School foundSchool = schoolRepository.findById(dto.getSchoolId()).orElseThrow(() -> new IllegalStateException("not found school id:" + dto.getSchoolId()));
    foundUser.setSchool(foundSchool);
    return new UserDTO(foundUser);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByUserEmail(email);
  }

  public UserProfileDTO getProfile(Integer userId) {
    return new UserProfileDTO(userRepository.findById(userId).orElseThrow(
        () -> new IllegalStateException("not found user id : " + userId))
    );
  }

  @Transactional
  public UserProfileDTO updateProfile(UserProfileDTO dto) {
    User foundUser = userRepository.findById(dto.getId()).orElseThrow(
        () -> new IllegalArgumentException("not found user id : " + dto.getId()));

    foundUser.setName(dto.getName());
    foundUser.setInterest(dto.getInterest());
    foundUser.setStatusMessage(dto.getStatusMessage());

    return dto;
  }

  @Transactional
  public void setAllUserStatusToOffline() {
    List<User> userList = userRepository.findAll();
    userList.forEach(
        user -> {
          user.setIsOnline(false);
          userRepository.save(user);
        }
    );
  }

  @Transactional
  public void setUserStatusToOnline(Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalStateException("not found user id : " + userId));

    // 현재 날짜 및 시간을 ISO 8601 형식으로 포맷
    DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
    String formattedDate = LocalDateTime.now().format(isoFormatter);

    user.setEnteredDate(formattedDate);
    user.setIsOnline(true);
  }

}
