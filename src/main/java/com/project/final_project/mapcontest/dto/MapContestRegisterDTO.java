package com.project.final_project.mapcontest.dto;

import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureDTO;
import com.project.final_project.furniture.dto.FurnitureRegisterDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapContestRegisterDTO {
  private String title;
  private String description;
  private String previewImageUrl;
  private Integer userId;
}
