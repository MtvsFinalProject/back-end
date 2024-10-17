package com.project.final_project.furniture.groundfurniture.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroundFurnitureRegisterDTO {
  private Integer objId;
  private Integer x;
  private Integer y;
  private Integer rot;
  private Integer userId;
  private Integer mapId;
}
