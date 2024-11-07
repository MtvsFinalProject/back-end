package com.project.final_project.myclassroom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureDTO;
import com.project.final_project.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "my_classroom")
@Getter @Setter
@Builder
@AllArgsConstructor
public class MyClassroom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "my_classroom_view_count")
  private Integer viewCount;

  @Column(name = "my_classroom_preview_image_url")
  private String previewImageUrl;

  @Column(name = "my_classroom_like_count")
  private Integer likeCount;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Furniture> furnitureList = new ArrayList<>();

  @Column(name = "my_classroom_background_color")
  private String backgroundColor;

  @Column(name = "my_classroom_alpha")
  private Integer alpha;

  protected MyClassroom() {}
}

