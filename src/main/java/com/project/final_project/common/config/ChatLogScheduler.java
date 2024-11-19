package com.project.final_project.common.config;

import com.project.final_project.chatlog.domain.ChatLog;
import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SchedulerConfig {

  private final JobLauncher jobLauncher;
  private final Job chatLogJob;
  private Long lastProcessedId = 0L; // 오프셋 초기화

  @Scheduled(fixedDelay = 60000) // 1분마다 실행
  public void runChatLogJob() {
    try {
      log.info("ChatLog Job 시작 (lastProcessedId = {})", lastProcessedId);

      // JobParameters에 lastProcessedId 전달
      JobParameters jobParameters = new JobParametersBuilder()
          .addLong("lastProcessedId", lastProcessedId) // 오프셋 전달
          .addLong("time", System.currentTimeMillis()) // 고유 파라미터
          .toJobParameters();

      // Job 실행
      JobExecution jobExecution = jobLauncher.run(chatLogJob, jobParameters);

      // 마지막 처리된 ID 업데이트 (JobExecution의 ExitStatus에서 관리 가능)
      String lastProcessedIdParam = jobExecution.getJobParameters().getString("lastProcessedId");
      if (lastProcessedIdParam != null) {
        lastProcessedId = Long.parseLong(lastProcessedIdParam);
        log.info("ChatLog Job 완료. 업데이트된 lastProcessedId = {}", lastProcessedId);
      }

    } catch (Exception e) {
      log.error("ChatLog Job 실행 중 오류 발생: ", e);
    }
  }
}
