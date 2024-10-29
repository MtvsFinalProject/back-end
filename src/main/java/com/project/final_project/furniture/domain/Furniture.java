package com.project.final_project.furniture.domain;
import com.project.final_project.myclassroom.domain.MyClassroom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "map_id")
  private Integer mapId;

  @ManyToOne
  @JoinColumn(name = "my_classroom_id") // 외래 키 컬럼 명 설정
  private MyClassroom myClassroom;
}
