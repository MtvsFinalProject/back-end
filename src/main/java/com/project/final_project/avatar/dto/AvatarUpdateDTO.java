package com.project.final_project.avatar.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AvatarUpdateDTO {
  Integer userId;
  List<Integer> infoList;
}
