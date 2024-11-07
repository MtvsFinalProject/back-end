package com.project.final_project.furniture.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "furniture")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Furniture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "obj_id")
  private Integer objId;

  @Column(name = "x")
  private Integer x;

  @Column(name = "y")
  private Integer y;

  @Column(name = "rotation")
  private Integer rot;

  @Column(name = "flip")
  private Boolean flip;

  @Column(name = "map_id")
  private Integer mapId;

  // 교실인지 학교인지 타입 구분 데이터 추가
  @Column(name = "map_type")
  private String mapType;
}
