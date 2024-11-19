package com.project.final_project.common.config;

import com.project.final_project.airecommendation.domain.AIRecommendation;
import com.project.final_project.airecommendation.dto.AIResponseDTO;
import com.project.final_project.airecommendation.service.AIRecommendationService;
import com.project.final_project.chatlog.domain.ChatLog;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
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
public class ChatLogBatchConfig {

  private final DataSource dataSource;
  private final AIRecommendationService aiService;

  @Bean
  public Job chatLogJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
    return new JobBuilder("chatLogJob", jobRepository)
        .start(chatLogStep(jobRepository, transactionManager))  // senderId는 JobParameters에서 처리됨
        .build();
  }

  @Bean
  public Step chatLogStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
    return new StepBuilder("chatLogStep", jobRepository)
        .<ChatLog, ChatLog>chunk(10, transactionManager)
        .reader(chatLogReader(null))
        .writer(chatLogWriter(null))
        .build();
  }

  @Bean
  @StepScope
  public JdbcPagingItemReader<ChatLog> chatLogReader(@Value("#{jobParameters['userId']}") Long userId) throws Exception {
    MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
    queryProvider.setSelectClause("SELECT id, message, timestamp, sender_id");
    queryProvider.setFromClause("FROM chat_log");
    queryProvider.setWhereClause("WHERE sender_id = :senderId");
    queryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));

    return new JdbcPagingItemReaderBuilder<ChatLog>()
        .dataSource(dataSource)
        .fetchSize(10)
        .rowMapper(new BeanPropertyRowMapper<>(ChatLog.class))
        .queryProvider(queryProvider)
        .parameterValues(Collections.singletonMap("senderId", userId))  // senderId 파라미터 전달
        .name("chatLogReader")
        .build();
  }

  @Bean
  @StepScope
  public ItemWriter<ChatLog> chatLogWriter(@Value("#{jobParameters['userId']}") Long userId) {
    return items -> {
      if (!items.isEmpty()) {

        // AI 서버에 전달할 메시지를 단순 문자열 리스트로 변환
        List<String> messages = items.getItems().stream()
            .map(ChatLog::getMessage)
            .collect(Collectors.toList());

        AIResponseDTO aiResponse = aiService.sendChatLogToAI(messages, userId); // 문자열 리스트 전달


        if(aiResponse != null) {

          for (AIResponseDTO.RecommendedUser recommendedUser : aiResponse.getRecommended_users()) {
            // recommendedUser -> senderId, message, similarity
            if(!aiService.isExistRecommendedUser(userId.intValue(), recommendedUser.getSenderId())){
              aiService.saveRecommendation(
                  new AIRecommendation(
                      userId.intValue(),
                      recommendedUser.getSenderId(),
                      recommendedUser.getSimilarity(),
                      recommendedUser.getMessage(),
                      recommendedUser.getInterests()
                  )
              );
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