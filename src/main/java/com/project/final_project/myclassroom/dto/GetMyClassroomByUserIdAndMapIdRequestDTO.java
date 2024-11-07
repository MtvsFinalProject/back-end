package com.project.final_project.myclassroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMyClassroomByUserIdAndMapIdRequestDTO {

  Integer userId;
  Integer mapId;

}
