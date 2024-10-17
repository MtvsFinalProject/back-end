package com.project.final_project.user.controller;

import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import com.project.final_project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<User> getUserById(@RequestParam("userId") Integer id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  @PostMapping
  public ResponseEntity<User> registerUser(UserRegisterDTO dto){
    return ResponseEntity.ok(userService.registerUser(dto));
  }

  @PatchMapping
  public ResponseEntity<User> updateUser(UserUpdateDTO dto){
    return ResponseEntity.ok(userService.updateUser(dto));
  }

  @DeleteMapping
  public ResponseEntity<?> deleteUser(@RequestParam("userId") Integer id){
    userService.removeUser(id);
    return ResponseEntity.noContent().build();
  }

}
