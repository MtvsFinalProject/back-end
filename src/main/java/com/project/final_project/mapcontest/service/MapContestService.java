package com.project.final_project.mapcontest.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureDTO;
import com.project.final_project.mapcontest.domain.MapContest;
import com.project.final_project.mapcontest.dto.MapContestDTO;
import com.project.final_project.mapcontest.dto.MapContestRegisterDTO;
import com.project.final_project.mapcontest.repository.MapContestRepository;
import com.project.final_project.myclassroom.domain.MyClassroom;
import com.project.final_project.myclassroom.repository.MyClassroomRepository;
import com.project.final_project.user.domain.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MapContestService {

  private final MapContestRepository mapContestRepository;
  private final MyClassroomRepository myClassroomRepository;

  @Value("${cloud.aws.s3.bucket}")
  private String BUCKET;
  private final AmazonS3 amazonS3;

  public MapContestDTO registerMapContest(MapContestRegisterDTO dto) {

    MyClassroom foundMyClassroom = myClassroomRepository.getMyClassroomListByUserId(dto.getUserId());

    // 깊은 복사 수행
    List<Furniture> copiedFurnitureList = foundMyClassroom.getFurnitureList().stream()
        .map(furniture -> new Furniture(furniture)) // Furniture 클래스에 복사 생성자 필요
        .collect(Collectors.toList());

    MapContest mapContest = MapContest.builder()
        .userId(dto.getUserId())
        .title(dto.getTitle())
        .description(dto.getDescription())
        .furnitureList(copiedFurnitureList) // 독립된 복사본 사용
        .previewImageUrl(dto.getPreviewImageUrl())
        .likeCount(0)
        .viewCount(0)
        .build();

    MapContest savedMapContest = mapContestRepository.save(mapContest);

    return new MapContestDTO(savedMapContest);
  }



  public void upload(MultipartFile file, String fileName) {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(file.getContentType());
      metadata.setContentLength(file.getSize());
      amazonS3.putObject(BUCKET + "/post", fileName, file.getInputStream(), metadata);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(String fileName) {
    DeleteObjectRequest request = new DeleteObjectRequest(BUCKET + "/post", fileName);
    amazonS3.deleteObject(request);
  }

  public URL getUrl(String fileName) {
    return amazonS3.getUrl(BUCKET + "/post", fileName);
  }

  public void removeMapContest(Integer mapContestId) {
    MapContest foundMapContest = mapContestRepository.findById(mapContestId).orElseThrow(
        () -> new IllegalStateException("not found mapContest id : " + mapContestId));

    // s3에 등록된 이미지 삭제
    String fileName = String.format("%s_%s", foundMapContest.getUserId(), foundMapContest.getTitle());
    delete(fileName);

    mapContestRepository.deleteById(mapContestId);
  }

  public List<MapContestDTO> getAllFurnitureList() {
    return mapContestRepository.findAll().stream().map(MapContestDTO::new).toList();
  }

  public List<FurnitureDTO> getFurnitureListByMapContestId(Integer mapContestId) {
    MapContest mapContest = mapContestRepository.findById(mapContestId).orElseThrow(
        () -> new IllegalStateException("not found mapcontest id : " + mapContestId));
    return mapContest.getFurnitureList().stream().map(FurnitureDTO::new).toList();
  }
}
