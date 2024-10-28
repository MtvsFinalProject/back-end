package com.project.final_project.chatlog.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.chatlog.dto.ChatLogRequestDTO;
import com.project.final_project.chatlog.dto.ChatLogResponseDTO;
import com.project.final_project.chatlog.service.ChatLogService;
import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
@Slf4j
public class ChatLogController {

  private final ChatLogService chatLogService;

  @PostMapping
  public ResponseEntity<ChatLogRequestDTO> saveChatLog(@RequestBody ChatLogRequestDTO chatLogRequestDTO) {
    return ResponseEntity.ok(chatLogService.saveChatLog(chatLogRequestDTO));
  }

  @GetMapping
  public ResponseEntity<List<ChatLogResponseDTO>> getChatLogsBySenderId(@RequestParam("senderId") int senderId) {
    return ResponseEntity.ok(chatLogService.getChatLogsBySenderId(senderId));
  }

  @GetMapping("/list")
  public ResponseResult<List<ChatLogResponseDTO>> getAllChatLogs() {
    List<ChatLogResponseDTO> allChatLogs = chatLogService.getAllChatLogs();
    return success(allChatLogs);
  }
}
