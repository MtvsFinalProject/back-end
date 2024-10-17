package com.project.final_project.furniture.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.final_project.furniture.groundfurniture.dto.GroundFurnitureRegisterDTO;
import com.project.final_project.furniture.groundfurniture.service.GroundFurnitureService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FurnitureInitializer {

  private final GroundFurnitureService groundFurnitureService;
  private static final Logger logger = LoggerFactory.getLogger(FurnitureInitializer.class);

  @PostConstruct
  public void init() {
    // Hibernate 쿼리 로그 비활성화
    disableHibernateQueryLogging();

    // JSON 파일 경로 설정 (더미 데이터 파일 경로)
    File file = new File("src/main/resources/dummy_furniture_data_30.txt");
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      // 파일에서 JSON 데이터를 읽어와 DTO 리스트로 변환
      List<GroundFurnitureRegisterDTO> furnitureData = objectMapper.readValue(file, new TypeReference<>() {});

      // 데이터베이스에 저장
      for (GroundFurnitureRegisterDTO furniture : furnitureData) {
        groundFurnitureService.registerGroundFurniture(furniture);
      }
      System.out.println("가구 데이터가 성공적으로 저장되었습니다.");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // Hibernate 쿼리 로그 다시 활성화
      enableHibernateQueryLogging();
    }
  }

  private void disableHibernateQueryLogging() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    loggerContext.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
    loggerContext.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.OFF);
  }

  private void enableHibernateQueryLogging() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    loggerContext.getLogger("org.hibernate.SQL").setLevel(Level.DEBUG);
    loggerContext.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.DEBUG);
  }
}
