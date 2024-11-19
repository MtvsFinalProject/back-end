package com.project.final_project.common.config;

import com.project.final_project.airecommendation.domain.AIRecommendation;
import com.project.final_project.airecommendation.dto.AIResponseDTO;
import com.project.final_project.airecommendation.dto.RecommendResponseDTO;
import com.project.final_project.airecommendation.service.AIRecommendationService;
import com.project.final_project.chatlog.domain.ChatLog;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

  private final DataSource dataSource;
  private final AIRecommendationService aiService;

  @Bean
  public Job chatLogJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
    return new JobBuilder("chatLogJob", jobRepository)
        .start(chatLogStep(jobRepository, transactionManager))
        .build();
  }

  @Bean
  public Step chatLogStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
    return new StepBuilder("chatLogStep", jobRepository)
        .<ChatLog, ChatLog>chunk(1000, transactionManager)  // 한 번에 큰 단위로 처리
        .reader(chatLogReader(null))
        .writer(chatLogWriter(null))
        .build();
  }

  @Bean
  @StepScope
  public JdbcCursorItemReader<ChatLog> chatLogReader(@Value("#{jobParameters['lastProcessedId']}") Long lastProcessedId) throws Exception {
    String query = "SELECT id, message, timestamp, sender_id FROM chat_log WHERE id > :lastProcessedId ORDER BY id ASC";

    return new JdbcCursorItemReaderBuilder<ChatLog>()
        .dataSource(dataSource)
        .sql(query)
        .rowMapper(new BeanPropertyRowMapper<>(ChatLog.class))
        .queryArguments(Collections.singletonMap("lastProcessedId", lastProcessedId)) // 오프셋 전달
        .name("chatLogReader")
        .build();
  }

  @Bean
  @StepScope
  public ItemWriter<ChatLog> chatLogWriter(@Value("#{jobParameters['userId']}") Long userId) {
    return items -> {
      if (!items.isEmpty()) {
        AIResponseDTO aiResponse = aiService.sendChatLogToAI(items, userId);
        System.out.println(aiResponse);

        if (aiResponse != null) {
          for (AIResponseDTO.RecommendedUser recommendedUser : aiResponse.getRecommended_users()) {
            if (!aiService.isExistRecommendedUser(userId.intValue(), recommendedUser.getSenderId())) {
              aiService.saveRecommendation(
                  new AIRecommendation(
                      userId.intValue(),
                      recommendedUser.getSenderId(),
                      recommendedUser.getSimilarity(),
                      recommendedUser.getMessage(),
                      recommendedUser.getInterests()
                  )
              );
            } else {
              AIRecommendation recommendation = aiService.getRecommendataionInfo(
                  userId.intValue(), recommendedUser.getSenderId());

              aiService.updateRecommendation(recommendation.getId(), recommendedUser);
            }
          }
        }
      }
    };
  }

  @Bean
  public JobOperator jobOperator(JobLauncher jobLauncher, JobRepository jobRepository, JobExplorer jobExplorer, JobRegistry jobRegistry) {
    SimpleJobOperator jobOperator = new SimpleJobOperator();
    jobOperator.setJobLauncher(jobLauncher);
    jobOperator.setJobRepository(jobRepository);
    jobOperator.setJobExplorer(jobExplorer);
    jobOperator.setJobRegistry(jobRegistry);
    return jobOperator;
  }
}
