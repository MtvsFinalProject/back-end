package com.project.final_project.schedule.service;

import com.project.final_project.user.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatusService {
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private ScheduledFuture<?> scheduledFuture;
  private final Set<Integer> userIds = new HashSet<>();
  private UserService userService;

  @Autowired
  public UserStatusService(UserService userService) {
    this.userService = userService;
  }

  // 60초 타이머 시작
  public void startTimer() {
    scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
      // 타이머가 끝날 때마다 유저 상태 초기화
      userService.setAllUserStatusToOffline();

      System.out.print("userIds : ");
      for (Integer userId : userIds) {
        System.out.print(userId + ", ");
        userService.setUserStatusToOnline(userId);
      }
      System.out.println();

      userIds.clear();
    }, 0, 60, TimeUnit.SECONDS); // 60초 타이머
  }

  // 특정 유저 상태를 온라인으로 설정
  public synchronized void addOnlineUser(Integer userId) {
    userIds.add(userId);
  }

  @PostConstruct
  public void init() {
    startTimer();
  }
}
