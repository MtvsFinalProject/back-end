package com.project.final_project.furniture.groundfurniture.repository;

import com.project.final_project.furniture.groundfurniture.domain.GroundFurniture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroundFurnitureRepository extends JpaRepository<GroundFurniture, Integer> {
  @Query("select g from GroundFurniture g where g.userId = :userId")
  List<GroundFurniture> getGroundFurnitureByUserId(@Param("userId") String userId);

  @Query("select g from GroundFurniture g where g.mapId = :mapId")
  List<GroundFurniture> getGroundFurnitureByMapId(@Param("mapId") String mapId);
}
