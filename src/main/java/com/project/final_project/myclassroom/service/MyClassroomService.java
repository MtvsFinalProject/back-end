package com.project.final_project.myclassroom.service;

import com.project.final_project.furniture.domain.Furniture;
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

  public MyClassroomDTO registerMyClassroom(MyClassroomRegisterDTO dto) {

    User foundUser = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("not found user id : " + dto.getUserId()));

    MyClassroom newMyClassroom = MyClassroom.builder()
        .user(foundUser)
        .viewCount(0)
        .previewImageUrl(dto.getPreviewImageUrl())
        .likeCount(0)
        .backgroundColor(dto.getBackgroundColor())
        .alpha(dto.getAlpha())
        .build();

    MyClassroom savedRoom = myClassroomRepository.save(newMyClassroom);

    return new MyClassroomDTO(savedRoom);
  }

  public MyClassroomDTO getMyClassroomByUserIdAndMapId(GetMyClassroomByUserIdAndMapIdRequestDTO dto) {
    return new MyClassroomDTO(myClassroomRepository.getMyClassroomByUserIdAndMapId(dto.getUserId(), dto.getMapId()));
  }

  public MyClassroomDTO getMyClassroomByMapId(Integer mapId) {
    return new MyClassroomDTO(myClassroomRepository.getMyClassroomByMapId(mapId));
  }

  @Transactional
  public MyClassroomDTO addFurnitureToMyClassroom(AddFurnitureToMyClassroomDTO dto) {
    MyClassroom myClassroom = myClassroomRepository.getMyClassroomByMapId(dto.getMapId());
    Furniture foundFurniture = furnitureRepository.findById(
        dto.getFurnitureId()).orElseThrow(() -> new IllegalArgumentException("not found furniture id : " + dto.getFurnitureId()));
    List<Furniture> furnitureList = myClassroom.getFurnitureList();
    furnitureList.add(foundFurniture);
    return new MyClassroomDTO(myClassroom);
  }
}
