package com.project.final_project.furniture.repository;

import com.project.final_project.furniture.domain.Furniture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {
  @Query("select g from Furniture g where g.userId = :userId")
  List<Furniture> getGroundFurnitureByUserId(@Param("userId") String userId);

  @Query("select g from Furniture g where g.mapId = :mapId")
  List<Furniture> getGroundFurnitureByMapId(@Param("mapId") String mapId);
}
