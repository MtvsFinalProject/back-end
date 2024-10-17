package com.project.final_project.furniture.groundfurniture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroundFurnitureResponseDTO {
  private Integer id;
  private Integer objId;
  private Integer x;
  private Integer y;
  private Integer rot;
  private Integer userId;
  private Integer mapId;
}
