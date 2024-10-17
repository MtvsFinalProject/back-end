package com.project.final_project.chatlog.controller;

import com.project.final_project.chatlog.dto.RequestChatLogDTO;
import com.project.final_project.chatlog.dto.ResponseChatLogDTO;
import com.project.final_project.chatlog.service.ChatLogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-log")
@RequiredArgsConstructor
public class ChatLogController {

  private final ChatLogService chatLogService;

  @PostMapping
  public ResponseEntity<RequestChatLogDTO> saveChatLog(@RequestBody RequestChatLogDTO requestChatLogDTO) {
    return ResponseEntity.ok(chatLogService.saveChatLog(requestChatLogDTO));
  }

  @GetMapping
  public ResponseEntity<List<ResponseChatLogDTO>> getChatLogsBySenderId(@RequestParam("senderId") int senderId) {
    return ResponseEntity.ok(chatLogService.getChatLogsBySenderId(senderId));
  }

}
