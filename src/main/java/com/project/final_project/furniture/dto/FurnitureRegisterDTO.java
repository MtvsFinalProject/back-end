package com.project.final_project.furniture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureRegisterDTO {
  private Integer objId;
  private Integer x;
  private Integer y;
  private Integer rot;
  private Integer userId;
  private Integer mapId;
}
