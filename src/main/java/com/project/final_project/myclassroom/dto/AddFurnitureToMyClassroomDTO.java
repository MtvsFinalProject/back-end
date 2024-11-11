package com.project.final_project.myclassroom.dto;

import com.project.final_project.furniture.domain.Furniture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddFurnitureToMyClassroomDTO {

  Integer mapId;
  Integer furnitureId;

}
