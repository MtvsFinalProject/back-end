package com.project.final_project.common.init;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.final_project.chatlog.dto.ChatLogRequestDTO;
import com.project.final_project.chatlog.service.ChatLogService;
import com.project.final_project.furniture.dto.FurnitureRegisterDTO;
import com.project.final_project.furniture.service.FurnitureService;
import com.project.final_project.school.dto.SchoolRegisterDTO;
import com.project.final_project.school.service.SchoolService;
import com.project.final_project.user.dto.UserRegisterDTO;
import com.project.final_project.user.service.UserService;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Init {

  private final ChatLogService chatLogService;
  private final FurnitureService furnitureService;
  private final SchoolService schoolService;
  private final UserService userService;

  @PostConstruct
  public void init() {

    // Hibernate 쿼리 로그 비활성화
    disableHibernateQueryLogging();

    if (!chatLogService.existsChatLogs()) {
      File file = new File("src/main/resources/chat_dummy1.txt");
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        List<ChatLogRequestDTO> chatLogs = objectMapper.readValue(file, new TypeReference<>() {});

        for (ChatLogRequestDTO chatLog : chatLogs) {
          chatLogService.saveChatLog(chatLog);
        }
        System.out.println("채팅 데이터가 성공적으로 저장되었습니다.");
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        // Hibernate 쿼리 로그 다시 활성화
        enableHibernateQueryLogging();
      }
    }



    if(!furnitureService.existsChatLogs()){
      File file = new File("src/main/resources/dummy_furniture_data_30.txt");
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        // 파일에서 JSON 데이터를 읽어와 DTO 리스트로 변환
        List<FurnitureRegisterDTO> furnitureData = objectMapper.readValue(file, new TypeReference<>() {});

        // 데이터베이스에 저장
        for (FurnitureRegisterDTO furniture : furnitureData) {
          furnitureService.registerFurniture(furniture);
        }
        System.out.println("가구 데이터가 성공적으로 저장되었습니다.");
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        // Hibernate 쿼리 로그 다시 활성화
        enableHibernateQueryLogging();
      }
    }

    if(!schoolService.existSchoolDatas()){
      File file = new File("src/main/resources/dummy_all_school.txt");
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        List<SchoolRegisterDTO> schools = objectMapper.readValue(file, new TypeReference<>() {});

        for (SchoolRegisterDTO school : schools) {
          schoolService.registerSchool(school);
        }

        System.out.println("학교 데이터가 성공적으로 저장되었습니다.");
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        // Hibernate 쿼리 로그 다시 활성화
        enableHibernateQueryLogging();
      }
    }



    if(!userService.existUserDatas()){
      File file = new File("src/main/resources/dummy_users.txt");
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        List<UserRegisterDTO> users = objectMapper.readValue(file, new TypeReference<>() {});

        for (UserRegisterDTO user : users) {
          userService.registerUser(user);
        }

        System.out.println("유저 데이터가 성공적으로 저장되었습니다.");
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        // Hibernate 쿼리 로그 다시 활성화
        enableHibernateQueryLogging();
      }
    }

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
