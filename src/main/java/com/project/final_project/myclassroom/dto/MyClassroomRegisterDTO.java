package com.project.final_project.myclassroom.dto;

import com.project.final_project.furniture.domain.Furniture;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyClassroomRegisterDTO {

  Integer userId;
  String previewImageUrl;
  String backgroundColor;
  Integer alpha;
}
