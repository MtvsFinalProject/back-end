package com.project.final_project.avatar.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AvatarResponseDTO {

  Integer id;
  List<Integer> infoList;

}
