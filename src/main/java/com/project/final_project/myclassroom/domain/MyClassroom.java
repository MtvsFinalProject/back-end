package com.project.final_project.myclassroom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureDTO;
import com.project.final_project.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "my_classroom_view_count")
  private Integer viewCount;

  @Column(name = "my_classroom_like_count")
  private Integer likeCount;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinTable(
      name = "my_classroom_furniture_list",
      joinColumns = @JoinColumn(name = "my_classroom_id"),
      inverseJoinColumns = @JoinColumn(name = "furniture_id")
  )
  private List<Furniture> furnitureList = new ArrayList<>();

  protected MyClassroom() {}
}
