package com.project.final_project.user.service;

import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import com.project.final_project.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getUser(Integer id) {
    return userRepository.findById(id).orElse(null);
  }

  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  public User registerUser(UserRegisterDTO dto){
    User foundUser = userRepository.findByUserEmail(dto.getEmail());
    if(foundUser != null){
      throw new IllegalStateException("이미 존재하는 유저 이메일 입니다.");
    }
    User newUser = User.createUser(dto);
    return userRepository.save(newUser);
  }

  public User updateUser(UserUpdateDTO dto) {
    User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalStateException("not found user id:" + dto.getUserId()));
    return foundUser.updateUser(dto);
  }

  public void removeUser(Integer id) {
    userRepository.deleteById(id);
  }

  public void gainExp(Integer id, Integer exp) {
    User foundUser = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("not found user id:" + id));
    foundUser.gainExp(exp);
    userRepository.save(foundUser);
  }
}
