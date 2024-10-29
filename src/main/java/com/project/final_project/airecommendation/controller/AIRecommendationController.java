package com.project.final_project.airecommendation.controller;

import com.project.final_project.airecommendation.domain.AIRecommendation;
import com.project.final_project.airecommendation.repository.AIRecommendationRepository;
import com.project.final_project.airecommendation.service.AIRecommendationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai-recommend")
@RequiredArgsConstructor
public class AIRecommendationController {

  private final AIRecommendationService aiRecommendationService;


  @GetMapping
  public List<Integer> getRecommendedFriendIds(@RequestParam("userId") Integer userId) {
    return aiRecommendationService.getRecommendedFriendIds(userId);
  }

  @GetMapping("/name-list")
  public List<String> getRecommendedFriendNames(@RequestParam("userId") Integer userId) {
    return aiRecommendationService.getRecommendedFriendNames(userId);
  }

}

