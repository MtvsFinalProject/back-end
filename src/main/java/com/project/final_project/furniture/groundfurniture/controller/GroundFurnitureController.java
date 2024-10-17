package com.project.final_project.furniture.groundfurniture.controller;

import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureRegisterDTO;
import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureResponseDTO;
import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureUpdateDTO;
import com.project.final_project.furniture.groundfurniture.service.GroundFurnitureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ground-furniture")
@RequiredArgsConstructor
public class GroundFurnitureController {

  private final GroundFurnitureService groundFurnitureService;

  @PostMapping
  public ResponseEntity<Integer> registerGroundFurniture(
      @RequestBody GroundFurnitureRegisterDTO groundFurnitureRegisterDTO) {
    return ResponseEntity.ok(groundFurnitureService.registerGroundFurniture(groundFurnitureRegisterDTO));
   }

  @GetMapping("/user")
  public ResponseEntity<List<GroundFurnitureResponseDTO>> getGroundFurnitureByUserId(@RequestParam("userId") String userId) {
    return ResponseEntity.ok(groundFurnitureService.getGroundFurnitureByUserId(userId));
  }

  @GetMapping("/map")
  public ResponseEntity<List<GroundFurnitureResponseDTO>> getGroundFurnitureByMapId(@RequestParam("mapId") String mapId) {
    return ResponseEntity.ok(groundFurnitureService.getGroundFurnitureByMapId(mapId));
  }

  @PatchMapping
  public ResponseEntity<GroundFurnitureUpdateDTO> updateGroundFurniture(@RequestBody
      GroundFurnitureUpdateDTO groundFurnitureUpdateDTO) {
    return ResponseEntity.ok(groundFurnitureService.updateGroundFurnitureUpdateDTO(groundFurnitureUpdateDTO));
  }

  @DeleteMapping
  public ResponseEntity<?> removeGroundFurniture(@RequestParam("furnitureId") Integer furnitureId) {
    groundFurnitureService.deleteFurniture(furnitureId);
    return ResponseEntity.noContent().build();
  }
}
