package com.project.final_project.user.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.dto.UserProfileDTO;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.dto.UserRegisterSchoolDTO;
import com.project.final_project.user.dto.UserUpdateDTO;
import com.project.final_project.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public UserDTO getUserById(@RequestParam("userId") Integer id) {
    User user = userService.getUser(id);
    return new UserDTO(user);
  }

  @GetMapping("/email")
  public UserDTO getUserByEmail(@RequestParam("email") String email){
    User foundUser = userService.getUserByEmail(email);
    return new UserDTO(foundUser);
  }

  @GetMapping("/list")
  public ResponseResult<List<UserDTO>> getAllUsers() {
    List<UserDTO> allUser = userService.getAllUser();
    return success(allUser);
  }

  @PostMapping
  public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterDTO dto){
    return ResponseEntity.ok(userService.registerUser(dto));
  }

  @PatchMapping
  public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO dto){
    return ResponseEntity.ok(userService.updateUser(dto));
  }

  @PatchMapping("/register-school")
  public ResponseResult<UserDTO> registerSchoolToUser(@RequestBody UserRegisterSchoolDTO dto){
    return success(userService.registerSchoolToUser(dto));
  }

  @DeleteMapping
  public ResponseEntity<?> deleteUser(@RequestParam("userId") Integer id){
    userService.removeUser(id);
    return ResponseEntity.noContent().build();
  }


  //== 프로필 ==//
  @GetMapping("/profile/{userId}")
  public ResponseResult<UserProfileDTO> getProfile(@PathVariable("userId") Integer userId) {
    return success(userService.getProfile(userId));
  }

  @PatchMapping("/profile")
  public ResponseResult<UserProfileDTO> updateProfile(@RequestBody UserUpdateDTO dto) {
    return success(new UserProfileDTO(userService.updateUser(dto)));
  }

}
