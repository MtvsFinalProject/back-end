package com.project.final_project.common.init;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.project.final_project.school.dto.SchoolRegisterDTO;
import com.project.final_project.school.service.SchoolService;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Init {

  private final SchoolService schoolService;

  @PostConstruct
  public void init() {
    // 데이터베이스에 학교 데이터가 없으면 초기화 작업 실행
    if (!schoolService.existSchoolDatas()) {
      File excelFile = new File("src/main/resources/schools.xlsx");

      if (!excelFile.exists()) {
        System.out.println("엑셀 파일이 존재하지 않습니다: " + excelFile.getPath());
        return;
      }

      try (FileInputStream inputStream = new FileInputStream(excelFile);
          Workbook workbook = new XSSFWorkbook(inputStream)) {

        // 엑셀 데이터를 읽어서 DTO 리스트로 변환
        List<SchoolRegisterDTO> schoolData = extractSchoolData(workbook);
        for (SchoolRegisterDTO school : schoolData) {
          schoolService.registerSchool(school); // 학교 데이터 등록
        }

        System.out.println("학교 데이터가 성공적으로 저장되었습니다.");

      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("학교 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
    }
  }

  // 엑셀 데이터를 읽어서 SchoolRegisterDTO 리스트로 변환
  private List<SchoolRegisterDTO> extractSchoolData(Workbook workbook) {
    List<SchoolRegisterDTO> schools = new ArrayList<>();
    Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트

    for (Row row : sheet) {
      if (row.getRowNum() == 0) continue; // 헤더 행 건너뛰기

      // 학교명, 소재지지번주소, 위도, 경도 컬럼 읽기
      String schoolName = row.getCell(1).getStringCellValue();
      String address = row.getCell(7).getStringCellValue();
      double latitude = row.getCell(15).getNumericCellValue(); // 위도
      double longitude = row.getCell(16).getNumericCellValue(); // 경도

      // SchoolRegisterDTO 생성 및 리스트에 추가
      SchoolRegisterDTO school = new SchoolRegisterDTO(schoolName, address, latitude, longitude);
      schools.add(school);
    }

    return schools;
  }

  // Hibernate SQL 로그 비활성화 메서드
  private void disableHibernateQueryLogging() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    // Hibernate 및 SQL 관련 모든 로거 비활성화
    loggerContext.getLogger("org.hibernate").setLevel(Level.OFF);
    loggerContext.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
    loggerContext.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.OFF);
    loggerContext.getLogger("org.hibernate.stat").setLevel(Level.OFF);
    loggerContext.getLogger("org.hibernate.tool.hbm2ddl").setLevel(Level.OFF);
    loggerContext.getLogger("javax.sql").setLevel(Level.OFF); // JDBC 관련 로그 비활성화
  }

  // Hibernate SQL 로그 다시 활성화 메서드
  private void enableHibernateQueryLogging() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    loggerContext.getLogger("org.hibernate.SQL").setLevel(Level.DEBUG);
    loggerContext.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.DEBUG);
    loggerContext.getLogger("org.hibernate.stat").setLevel(Level.DEBUG);
    loggerContext.getLogger("org.hibernate.tool.hbm2ddl").setLevel(Level.DEBUG);
    loggerContext.getLogger("javax.sql").setLevel(Level.DEBUG); // JDBC 로그 활성화
  }

}
