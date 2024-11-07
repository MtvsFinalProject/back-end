package com.project.final_project.school;

import com.project.final_project.school.dto.SchoolRegisterDTO;
import com.project.final_project.school.repository.SchoolRepository;
import com.project.final_project.school.service.SchoolService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class SchoolTest {

  @Autowired
  private SchoolService schoolService;

  @Autowired
  private SchoolRepository schoolRepository;

  @DisplayName("학교 등록 테스트")
  @Test
  @Transactional
  void registerSchoolTest() {
    SchoolRegisterDTO dto1 = new SchoolRegisterDTO("school1", "a");
    SchoolRegisterDTO dto2 = new SchoolRegisterDTO("school2", "b");
    SchoolRegisterDTO dto3 = new SchoolRegisterDTO("school3", "c");

    schoolService.registerSchool(dto1);
    schoolService.registerSchool(dto2);
    schoolService.registerSchool(dto3);
    Assertions.assertThat(schoolRepository.findAll().size()).isEqualTo(6);
  }

}
