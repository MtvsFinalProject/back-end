package com.project.final_project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FinalProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinalProjectApplication.class, args);


//    try {
//      // JSON 파일 로드
//      ObjectMapper objectMapper = new ObjectMapper();
//      JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/2024_middle_school_total_state.json"));
//
//      // body 배열 가져오기
//      ArrayNode bodyArray = (ArrayNode) rootNode.get("body");
//
//      // txt 파일 작성
//      FileWriter writer = new FileWriter("src/main/resources/dummy_all_school.txt");
//
//      // 데이터 추출 및 저장
//      writer.write("[\n");
//      for (JsonNode item : bodyArray) {
//        String location = item.get(2).asText();  // 지역
//        String schoolName = item.get(4).asText();  // 학교명
//
//        // SchoolRegisterDTO 형식으로 저장
//        writer.write("\t{ \"schoolName\": \"" + schoolName + "\", \"location\": \"" + location + "\" },\n");
//      }
//      writer.write("]");
//
//      // 파일 닫기
//      writer.close();
//      System.out.println("Data extraction and saving complete.");
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }



  }
}
