package com.project.final_project.common.config;

import com.project.final_project.airecommendation.domain.AIRecommendation;
import com.project.final_project.airecommendation.dto.AIResponseDTO;
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
public class BatchConfig {

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
        .<ChatLog, ChatLog>chunk(20, transactionManager)
        .reader(chatLogReader(null))
        .writer(chatLogWriter(null))
        .build();
  }

  @Bean
  @StepScope
  public JdbcPagingItemReader<ChatLog> chatLogReader(@Value("#{jobParameters['senderId']}") Long senderId) throws Exception {
    MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
    queryProvider.setSelectClause("SELECT id, message, timestamp, sender_id");
    queryProvider.setFromClause("FROM chat_log");
    queryProvider.setWhereClause("WHERE sender_id = :senderId");
    queryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));

    return new JdbcPagingItemReaderBuilder<ChatLog>()
        .dataSource(dataSource)
        .fetchSize(20)
        .rowMapper(new BeanPropertyRowMapper<>(ChatLog.class))
        .queryProvider(queryProvider)
        .parameterValues(Collections.singletonMap("senderId", senderId))  // senderId 파라미터 전달
        .name("chatLogReader")
        .build();
  }

  @Bean
  @StepScope
  public ItemWriter<ChatLog> chatLogWriter(@Value("#{jobParameters['senderId']}") Long senderId) {
    return items -> {
      if (!items.isEmpty()) {
        AIResponseDTO aiResponse = aiService.sendToAI(items, senderId);
        Set<Integer> uniqueRecommendedIds = new HashSet<>();
        AIRecommendation recommendation = aiService.getRecommendByUserId(senderId.intValue());

        if(recommendation == null){
          recommendation = new AIRecommendation();
          recommendation.setUserId(senderId.intValue());
          recommendation.setRecommendedFriendIds(new ArrayList<>());
        }

        if(aiResponse != null) {
          for (AIResponseDTO.RecommendedUser recommendedUser : aiResponse.getRecommended_users()) {
              uniqueRecommendedIds.add(recommendedUser.getSenderId());
          }

          // 기존 추천 친구 목록을 가져옴
          List<Integer> currentFriendIds = recommendation.getRecommendedFriendIds();

          // 중복되지 않도록 새로운 추천 친구 ID들을 추가
          currentFriendIds.addAll(uniqueRecommendedIds.stream()
              .filter(id -> !currentFriendIds.contains(id)
                  && !aiService.isExistBySenderIdAndFriendId(senderId.intValue(), id))  // 중복된 ID를 제외
              .toList());

          // 업데이트된 추천 친구 목록을 설정
          recommendation.setRecommendedFriendIds(currentFriendIds);

          // 업데이트된 recommendation을 저장
          aiService.saveRecommendation(recommendation);
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
