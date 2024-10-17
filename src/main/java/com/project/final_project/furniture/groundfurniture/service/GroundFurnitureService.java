package com.project.final_project.furniture.groundfurniture.service;

import com.project.final_project.furniture.groundfurniture.domain.GroundFurniture;
import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureRegisterDTO;
import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureResponseDTO;
import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureUpdateDTO;
import com.project.final_project.furniture.groundfurniture.repository.GroundFurnitureRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class GroundFurnitureService {

  private final GroundFurnitureRepository groundFurnitureRepository;

  public Integer registerGroundFurniture(
      @RequestBody GroundFurnitureRegisterDTO groundFurnitureRegisterDTO) {
    GroundFurniture newGroundFurniture = GroundFurniture.builder()
        .objId(groundFurnitureRegisterDTO.getObjId())
        .x(groundFurnitureRegisterDTO.getX())
        .y(groundFurnitureRegisterDTO.getY())
        .rot(groundFurnitureRegisterDTO.getRot())
        .userId(groundFurnitureRegisterDTO.getUserId())
        .mapId(groundFurnitureRegisterDTO.getMapId())
        .build();
    GroundFurniture savedFurniture = groundFurnitureRepository.save(newGroundFurniture);
    return savedFurniture.getId();
  }

  public List<GroundFurnitureResponseDTO> getGroundFurnitureByUserId(String userId) {
    List<GroundFurniture> groundFurnitureList = groundFurnitureRepository.getGroundFurnitureByUserId(
        userId);
    List<GroundFurnitureResponseDTO> result = groundFurnitureList.stream()
        .map(g -> new GroundFurnitureResponseDTO(g.getId(), g.getObjId(), g.getX(), g.getY(), g.getRot(), g.getUserId(), g.getMapId()))
        .toList();
    return result;
  }

  public List<GroundFurnitureResponseDTO> getGroundFurnitureByMapId(String mapId) {
    List<GroundFurniture> groundFurnitureList = groundFurnitureRepository.getGroundFurnitureByMapId(
        mapId);
    List<GroundFurnitureResponseDTO> result = groundFurnitureList.stream()
        .map(g -> new GroundFurnitureResponseDTO(g.getId(), g.getObjId(), g.getX(), g.getY(), g.getRot(), g.getUserId(), g.getMapId()))
        .toList();
    return result;
  }

  @Transactional
  public GroundFurnitureUpdateDTO updateGroundFurnitureUpdateDTO(GroundFurnitureUpdateDTO groundFurnitureUpdateDTO) {
    Integer id = groundFurnitureUpdateDTO.getId();
    GroundFurniture foundGroundFurniture = groundFurnitureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found furniture : " + id));
    foundGroundFurniture.setObjId(groundFurnitureUpdateDTO.getObjId());
    foundGroundFurniture.setX(groundFurnitureUpdateDTO.getX());
    foundGroundFurniture.setY(groundFurnitureUpdateDTO.getY());
    foundGroundFurniture.setRot(groundFurnitureUpdateDTO.getRot());
    foundGroundFurniture.setUserId(groundFurnitureUpdateDTO.getUserId());
    foundGroundFurniture.setMapId(groundFurnitureUpdateDTO.getMapId());
    return groundFurnitureUpdateDTO;
  }

  public void deleteFurniture(Integer furnitureId) {
    groundFurnitureRepository.deleteById(furnitureId);
  }
}
