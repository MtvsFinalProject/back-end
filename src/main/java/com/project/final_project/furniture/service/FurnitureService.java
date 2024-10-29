package com.project.final_project.furniture.service;

import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureRegisterDTO;
import com.project.final_project.furniture.dto.FurnitureResponseDTO;
import com.project.final_project.furniture.dto.FurnitureUpdateDTO;
import com.project.final_project.furniture.repository.FurnitureRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class FurnitureService {

  private final FurnitureRepository furnitureRepository;

  public Integer registerGroundFurniture(
      @RequestBody FurnitureRegisterDTO furnitureRegisterDTO) {
    Furniture newFurniture = Furniture.builder()
        .objId(furnitureRegisterDTO.getObjId())
        .x(furnitureRegisterDTO.getX())
        .y(furnitureRegisterDTO.getY())
        .rot(furnitureRegisterDTO.getRot())
        .userId(furnitureRegisterDTO.getUserId())
        .mapId(furnitureRegisterDTO.getMapId())
        .build();
    Furniture savedFurniture = furnitureRepository.save(newFurniture);
    return savedFurniture.getId();
  }

  public List<FurnitureResponseDTO> getGroundFurnitureByUserId(String userId) {
    List<Furniture> furnitureList = furnitureRepository.getGroundFurnitureByUserId(
        userId);
    List<FurnitureResponseDTO> result = furnitureList.stream()
        .map(g -> new FurnitureResponseDTO(g.getId(), g.getObjId(), g.getX(), g.getY(), g.getRot(), g.getUserId(), g.getMapId()))
        .toList();
    return result;
  }

  public List<FurnitureResponseDTO> getGroundFurnitureByMapId(String mapId) {
    List<Furniture> furnitureList = furnitureRepository.getGroundFurnitureByMapId(
        mapId);
    List<FurnitureResponseDTO> result = furnitureList.stream()
        .map(g -> new FurnitureResponseDTO(g.getId(), g.getObjId(), g.getX(), g.getY(), g.getRot(), g.getUserId(), g.getMapId()))
        .toList();
    return result;
  }

  @Transactional
  public FurnitureUpdateDTO updateGroundFurnitureUpdateDTO(FurnitureUpdateDTO furnitureUpdateDTO) {
    Integer id = furnitureUpdateDTO.getId();
    Furniture foundFurniture = furnitureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found furniture : " + id));
    foundFurniture.setObjId(furnitureUpdateDTO.getObjId());
    foundFurniture.setX(furnitureUpdateDTO.getX());
    foundFurniture.setY(furnitureUpdateDTO.getY());
    foundFurniture.setRot(furnitureUpdateDTO.getRot());
    foundFurniture.setUserId(furnitureUpdateDTO.getUserId());
    foundFurniture.setMapId(furnitureUpdateDTO.getMapId());
    return furnitureUpdateDTO;
  }

  public void deleteFurniture(Integer furnitureId) {
    furnitureRepository.deleteById(furnitureId);
  }

  public boolean existsChatLogs() {
    return furnitureRepository.count() > 0;
  }
}
