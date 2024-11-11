package com.project.final_project.gallery.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GalleryDTO {

  private Integer id;
  String imageBase64;
  String title;
  String description;
  Integer likeN;
  Integer visitN;

}
