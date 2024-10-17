package com.project.final_project.chatlog.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.final_project.chatlog.dto.RequestChatLogDTO;
import com.project.final_project.chatlog.repository.ChatLogRepository;
import com.project.final_project.chatlog.service.ChatLogService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatLogInitializer {

  private final ChatLogService chatLogService;

  private static final Logger logger = LoggerFactory.getLogger(ChatLogInitializer.class);

  @PostConstruct
  public void init() {
    // Hibernate 쿼리 로그 비활성화
    disableHibernateQueryLogging();

    // JSON 파일 경로 설정
//    File file = new File("src/main/resources/dummy_chat_data_300.txt");
    File file = new File("src/main/resources/dummy_chat_data_realistic_1000.txt");
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      List<RequestChatLogDTO> chatLogs = objectMapper.readValue(file, new TypeReference<>() {});

      for (RequestChatLogDTO chatLog : chatLogs) {
        chatLogService.saveChatLog(chatLog);
      }
      System.out.println("데이터가 성공적으로 저장되었습니다.");
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
