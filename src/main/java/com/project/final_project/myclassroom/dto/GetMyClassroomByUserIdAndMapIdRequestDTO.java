package com.project.final_project.myclassroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetMyClassroomByUserIdAndMapIdRequestDTO {

  Integer userId;
  Integer mapId;

}
