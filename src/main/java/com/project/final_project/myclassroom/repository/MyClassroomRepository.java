package com.project.final_project.myclassroom.repository;

import com.project.final_project.myclassroom.domain.MyClassroom;
import com.project.final_project.myclassroom.dto.MyClassroomDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyClassroomRepository extends JpaRepository<MyClassroom, Integer> {

  @Query("select c from MyClassroom c where c.user.id = :userId")
  MyClassroom getMyClassroomListByUserId(@Param("userId") Integer userId);

  @Query("select c from MyClassroom c where c.id = :mapId")
  MyClassroom getMyClassroomByMapId(@Param("mapId") Integer mapId);

  @Query("select c from MyClassroom c where c.user.id = :userId")
  MyClassroom findByUserId(@Param("userId") Integer userId);
}
