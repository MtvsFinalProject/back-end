package com.project.final_project.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.myclassroom.domain.MyClassroom;
import com.project.final_project.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "school")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "school_name")
  private String schoolName;

  @Column(name = "school_location")
  private String location;

  // 각각의 유저에 해당하는 myClassroom을 갖고올 수 있음.
  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<User> userList = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  private List<Furniture> furnitureList = new ArrayList<>();
}
