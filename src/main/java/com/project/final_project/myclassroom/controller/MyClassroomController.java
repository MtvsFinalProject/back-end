package com.project.final_project.myclassroom.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import com.project.final_project.myclassroom.dto.AddFurnitureToMyClassroomDTO;
import com.project.final_project.myclassroom.dto.GetMyClassroomByUserIdAndMapIdRequestDTO;
import com.project.final_project.myclassroom.dto.MyClassroomDTO;
import com.project.final_project.myclassroom.dto.MyClassroomRegisterDTO;
import com.project.final_project.myclassroom.service.MyClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-classroom")
@RequiredArgsConstructor
public class MyClassroomController {

  private final MyClassroomService myClassroomService;

  @PostMapping
  public ResponseResult<MyClassroomDTO> registerMyClassroom(@RequestBody MyClassroomRegisterDTO dto) {
    return success(myClassroomService.registerMyClassroom(dto));
  }

  @PostMapping("/add-furniture")
  public ResponseResult<MyClassroomDTO> addFurnitureToMyClassroom(@RequestBody AddFurnitureToMyClassroomDTO dto){
    return success(myClassroomService.addFurnitureToMyClassroom(dto));
  }

  @GetMapping("/user")
  public ResponseResult<MyClassroomDTO> getMyClassroomByUserId(@RequestBody GetMyClassroomByUserIdAndMapIdRequestDTO dto){
    return success(myClassroomService.getMyClassroomByUserIdAndMapId(dto));
  }

}
