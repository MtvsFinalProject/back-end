package com.project.final_project.myclassroom.repository;

import com.project.final_project.myclassroom.domain.MyClassroom;
import com.project.final_project.myclassroom.dto.MyClassroomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MyClassroomRepository extends JpaRepository<MyClassroom, Integer> {

  @Query("select c from MyClassroom c where c.user.id = :userId and c.id = :mapId")
  MyClassroom getMyClassroomByUserIdAndMapId(Integer userId, Integer mapId);

  @Query("select c from MyClassroom c where c.id = :mapId")
  MyClassroom getMyClassroomByMapId(Integer mapId);

}
