package com.project.final_project.myclassroom.dto;

import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.myclassroom.domain.MyClassroom;
import com.project.final_project.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyClassroomDTO {

  private Integer id;
  private User user;
  private Integer viewCount;
  private String previewImageUrl;
  private Integer likeCount;
  private List<Furniture> furnitureList = new ArrayList<>();
  private String backgroundColor;
  private Integer alpha;

  public MyClassroomDTO(MyClassroom room) {
    this.id = room.getId();
    this.user = room.getUser();
    this.viewCount = room.getViewCount();
    this.previewImageUrl = room.getPreviewImageUrl();
    this.likeCount = room.getLikeCount();
    this.furnitureList = room.getFurnitureList();
    this.backgroundColor = room.getBackgroundColor();
    this.alpha = room.getAlpha();
  }
}
