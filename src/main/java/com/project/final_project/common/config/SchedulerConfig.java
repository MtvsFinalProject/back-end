package com.project.final_project.common.config;

import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

  private final JobLauncher jobLauncher;
  private final Job chatLogJob;
  private final UserService userService;

//  @Scheduled(fixedDelay = 15000)
  public void runChatLogJob() {
    List<UserDTO> allUser = userService.getAllUser();  // 모든 유저 조회

    for (UserDTO user : allUser) {
      Long senderId = user.getId().longValue();  // 유저별 senderId 가져오기
      System.out.println("senderId = " + senderId);

      JobParameters jobParameters = new JobParametersBuilder()
          .addLong("senderId", senderId)  // JobParameters에 senderId 전달
          .addLong("time", System.currentTimeMillis())  // 고유한 파라미터
          .toJobParameters();

      try {
        jobLauncher.run(chatLogJob, jobParameters);
      } catch (Exception e) {
        System.err.println("Job 실행 중 오류 발생: " + e.getMessage());
      }
    }
  }
}
