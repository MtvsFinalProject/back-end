package com.project.final_project.school.service;

import static java.util.stream.Collectors.toList;

import com.project.final_project.furniture.domain.Furniture;
import com.project.final_project.furniture.dto.FurnitureRegisterDTO;
import com.project.final_project.furniture.repository.FurnitureRepository;
import com.project.final_project.furniture.service.FurnitureService;
import com.project.final_project.school.domain.School;
import com.project.final_project.school.dto.SchoolDTO;
import com.project.final_project.school.dto.SchoolRegisterDTO;
import com.project.final_project.school.repository.SchoolRepository;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolService {

  private final SchoolRepository schoolRepository;
  private final FurnitureService furnitureService;
  private final FurnitureRepository furnitureRepository;

  public boolean existSchoolDatas() {
    return schoolRepository.count() > 0; // 데이터가 있으면 true
  }

  public SchoolDTO registerSchool(SchoolRegisterDTO schoolRegisterDTO) {
    School school = School.builder()
        .schoolName(schoolRegisterDTO.getSchoolName())
        .location(schoolRegisterDTO.getLocation())
        .build();

    School savedSchool = schoolRepository.save(school);

    return new SchoolDTO(savedSchool);
  }

  @Transactional
  public SchoolDTO addUserToSchool(Integer schoolId, User user) {
    School foundSchool = schoolRepository.findById(schoolId).orElseThrow(
        () -> new IllegalArgumentException("not found school id : " + schoolId));

    foundSchool.getUserList().add(user);

    return new SchoolDTO(foundSchool);
  }

  public List<User> getUserListBySchoolId(Integer schoolId) {
    School foundSchool = schoolRepository.findById(schoolId).orElseThrow(
        () -> new IllegalArgumentException("not found school id : " + schoolId));

    return foundSchool.getUserList();
  }

  public School getSchoolById(Integer schoolId) {
    return schoolRepository.findById(schoolId).orElseThrow(
        () -> new IllegalArgumentException("not found school id : " + schoolId));
  }

  @Transactional
  public SchoolDTO addFurnitureToSchool(FurnitureRegisterDTO furnitureRegisterDTO, Integer schoolId) {
    Integer registeredFurnitureId = furnitureService.registerFurniture(furnitureRegisterDTO);

    School foundSchool = schoolRepository.findById(schoolId).orElseThrow(
        () -> new IllegalArgumentException("not found school id : " + schoolId));

    List<Furniture> furnitureList = foundSchool.getFurnitureList();

    Furniture foundFurniture = furnitureRepository.findById(registeredFurnitureId).orElseThrow(
        () -> new IllegalArgumentException("not found furniture id : " + registeredFurnitureId));

    furnitureList.add(foundFurniture);
    foundSchool.setFurnitureList(furnitureList);

    return new SchoolDTO(foundSchool);
  }

  public List<SchoolDTO> getAllSchool() {
    return schoolRepository.findAll().stream()
        .map(SchoolDTO::new)
        .toList();
  }

  public void deleteSchoolById(Integer schoolId) {
    schoolRepository.deleteById(schoolId);
  }

  public List<School> getSchoolListBySchoolName(String schoolName) {
    return schoolRepository.findBySchoolNameContaining(schoolName);
  }
}
