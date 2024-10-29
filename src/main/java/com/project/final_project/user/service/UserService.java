package com.project.final_project.user.service;

import com.project.final_project.school.domain.School;
import com.project.final_project.school.dto.SchoolDTO;
import com.project.final_project.school.service.SchoolService;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import com.project.final_project.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final SchoolService schoolService;

  public User getUser(Integer id) {
    return userRepository.findById(id).orElse(null);
  }

  public List<UserDTO> getAllUser() {

    return userRepository.findAll().stream()
        .map(user -> new UserDTO(
            user.getId(),
            user.getSocialId(),
            user.getName(),
            user.getAge(),
            user.getGrade(),
            user.getBirthday(),
            user.getGender(),
            user.getEmail(),
            user.getPassword(),
            user.getPhone(),
            user.getSchool() != null ? user.getSchool().getId(): null
            ))
        .collect(Collectors.toList());
  }

  public UserDTO registerUser(UserRegisterDTO dto){
    User foundUser = userRepository.findByUserEmail(dto.getEmail());
    if(foundUser != null){
      throw new IllegalStateException("이미 존재하는 유저 이메일 입니다.");
    }
    User newUser = createUser(dto);

    userRepository.save(newUser);
    return new UserDTO(
        dto.getSocialId(),
        dto.getName(),
        dto.getAge(),
        dto.getGrade(),
        dto.getBirthday(),
        dto.getGender(),
        dto.getEmail(),
        dto.getNickname(),
        dto.getPhone(),
        dto.getSchoolId()
    );
  }

  @Transactional
  public UserDTO updateUser(UserUpdateDTO dto) {
    User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(
        () -> new IllegalStateException("not found user id:" + dto.getUserId())
    );

    if(dto.getSchoolId() != null){
      foundUser.setSocialId(dto.getSocialId());
    }
    if (dto.getName() != null) {
      foundUser.setName(dto.getName());
    }
    if (dto.getAge() != null) {
      foundUser.setAge(dto.getAge());
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
    if (dto.getNickname() != null) {
      foundUser.setNickname(dto.getNickname());
    }
    if (dto.getPhone() != null) {
      foundUser.setPhone(dto.getPhone());
    }
    if(dto.getSchoolId() != null){
      School school = schoolService.getSchoolById(dto.getSchoolId());
      foundUser.setSchool(school);
    }

    return new UserDTO(
        dto.getSocialId(),
        dto.getName(),
        dto.getAge(),
        dto.getGrade(),
        dto.getBirthday(),
        dto.getGender(),
        dto.getEmail(),
        dto.getNickname(),
        dto.getPhone(),
        dto.getSchoolId()
    );
  }

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
    user.setAge(dto.getAge());
    user.setGrade(dto.getGrade());
    user.setBirthday(dto.getBirthday());
    user.setGender(dto.getGender());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setNickname(dto.getNickname());
    user.setPhone(dto.getPhone());

    // SchoolService를 사용해 schoolId로 School 객체를 조회
    if(dto.getSchoolId() != null) {
      School school = schoolService.getSchoolById(dto.getSchoolId());
      user.setSchool(school); // 조회한 School 객체를 User에 설정
    }

    user.setLevel(1);
    user.setExp(0);
    user.setMaxExp(100);

    return userRepository.save(user); // 저장
  }
}
