package com.project.final_project.school.repository;

import com.project.final_project.school.domain.School;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

  List<School> findBySchoolNameContaining(String schoolName);
}
