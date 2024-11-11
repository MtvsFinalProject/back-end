package com.project.final_project.myclassroom.service;

import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureRegisterDTO;
import com.project.final_project.furniture.repository.FurnitureRepository;
import com.project.final_project.furniture.service.FurnitureService;
import com.project.final_project.myclassroom.dto.AddFurnitureToMyClassroomDTO;
import com.project.final_project.myclassroom.domain.MyClassroom;
import com.project.final_project.myclassroom.dto.GetMyClassroomByUserIdAndMapIdRequestDTO;
import com.project.final_project.myclassroom.dto.MyClassroomDTO;
import com.project.final_project.myclassroom.dto.MyClassroomRegisterDTO;
import com.project.final_project.myclassroom.repository.MyClassroomRepository;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class MyClassroomService {

  private final MyClassroomRepository myClassroomRepository;
  private final UserRepository userRepository;
  private final FurnitureRepository furnitureRepository;

  @Transactional
  public MyClassroomDTO registerMyClassroom(MyClassroomRegisterDTO dto) {

    User foundUser = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("not found user id : " + dto.getUserId()));

    MyClassroom c = myClassroomRepository.findByUserId(foundUser.getId());

    if(c != null) {
      for (Furniture furniture : c.getFurnitureList()) {
        System.out.println(furniture);
      }
      furnitureRepository.deleteAll(c.getFurnitureList());
      c.getFurnitureList().clear();

      // 새로운 furnitureList의 요소들 영속화
      for (FurnitureRegisterDTO fr : dto.getFurnitureList()) {
          Furniture furniture = new Furniture(fr);
          c.getFurnitureList().add(furniture); // MyClassroom에 furniture 추가
      }
      myClassroomRepository.save(c);
    }

    else {
      // MyClassroom 객체 생성
      MyClassroom newMyClassroom = MyClassroom.builder()
          .user(foundUser)
          .viewCount(0)
          .likeCount(0)
          .furnitureList(new ArrayList<>()) // 빈 리스트로 초기화
          .build();

      // furnitureList의 각 요소를 영속화
      for (FurnitureRegisterDTO fr : dto.getFurnitureList()) {
        Furniture furniture = new Furniture(fr);
        if (furniture.getId() == null) { // 새 Furniture 객체일 경우
          furnitureRepository.save(furniture); // furniture 저장
        }
        newMyClassroom.getFurnitureList().add(furniture); // MyClassroom에 furniture 추가
      }

      // MyClassroom 저장
       c = myClassroomRepository.save(newMyClassroom);
    }

    return new MyClassroomDTO(c); // DTO 반환
  }


  public MyClassroomDTO getMyClassroomListByUserId(Integer userId) {
    return new MyClassroomDTO(myClassroomRepository.getMyClassroomListByUserId(userId));
  }

  public MyClassroomDTO getMyClassroomByMapId(Integer mapId) {
    return new MyClassroomDTO(myClassroomRepository.getMyClassroomByMapId(mapId));
  }

  public MyClassroomDTO addFurnitureToMyClassroom(AddFurnitureToMyClassroomDTO dto) {
    MyClassroom myClassroom = myClassroomRepository.getMyClassroomByMapId(dto.getMapId());
    Furniture foundFurniture = furnitureRepository.findById(dto.getFurnitureId())
        .orElseThrow(() -> new IllegalArgumentException("not found furniture id : " + dto.getFurnitureId()));

    myClassroom.getFurnitureList().add(foundFurniture);
    myClassroomRepository.save(myClassroom);
    return new MyClassroomDTO(myClassroom);
  }

}
