package com.project.final_project.chatlog.dto;

import com.project.final_project.chatlog.domain.ChatType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseChatLogDTO {
  private Integer senderId;
  private Integer receiverId;
  private String message;
  private String timestamp;
  private String channel;
  private ChatType chatType;
}
