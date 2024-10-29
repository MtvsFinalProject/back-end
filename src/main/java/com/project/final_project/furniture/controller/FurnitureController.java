package com.project.final_project.furniture.controller;

import com.project.final_project.furniture.dto.FurnitureRegisterDTO;
import com.project.final_project.furniture.dto.FurnitureResponseDTO;
import com.project.final_project.furniture.dto.FurnitureUpdateDTO;
import com.project.final_project.furniture.service.FurnitureService;
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
@RequestMapping("/furniture")
@RequiredArgsConstructor
public class FurnitureController {

  private final FurnitureService furnitureService;

  @PostMapping
  public ResponseEntity<Integer> registerGroundFurniture(
      @RequestBody FurnitureRegisterDTO furnitureRegisterDTO) {
    return ResponseEntity.ok(furnitureService.registerGroundFurniture(furnitureRegisterDTO));
   }

  @GetMapping("/user")
  public ResponseEntity<List<FurnitureResponseDTO>> getGroundFurnitureByUserId(@RequestParam("userId") String userId) {
    return ResponseEntity.ok(furnitureService.getGroundFurnitureByUserId(userId));
  }

  @GetMapping("/map")
  public ResponseEntity<List<FurnitureResponseDTO>> getGroundFurnitureByMapId(@RequestParam("mapId") String mapId) {
    return ResponseEntity.ok(furnitureService.getGroundFurnitureByMapId(mapId));
  }

  @PatchMapping
  public ResponseEntity<FurnitureUpdateDTO> updateGroundFurniture(@RequestBody
  FurnitureUpdateDTO furnitureUpdateDTO) {
    return ResponseEntity.ok(furnitureService.updateGroundFurnitureUpdateDTO(
        furnitureUpdateDTO));
  }

  @DeleteMapping
  public ResponseEntity<?> removeGroundFurniture(@RequestParam("furnitureId") Integer furnitureId) {
    furnitureService.deleteFurniture(furnitureId);
    return ResponseEntity.noContent().build();
  }
}
