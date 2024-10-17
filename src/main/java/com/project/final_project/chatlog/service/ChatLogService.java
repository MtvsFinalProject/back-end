package com.project.final_project.chatlog.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.final_project.chatlog.domain.ChatLog;
import com.project.final_project.chatlog.dto.RequestChatLogDTO;
import com.project.final_project.chatlog.dto.ResponseChatLogDTO;
import com.project.final_project.chatlog.repository.ChatLogRepository;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatLogService {

  private final ChatLogRepository chatLogRepository;

  public RequestChatLogDTO saveChatLog(RequestChatLogDTO requestChatLogDTO) {
    ChatLog chatLog = ChatLog.builder()
        .sender_id(requestChatLogDTO.getSenderId())
        .receiver_id(requestChatLogDTO.getReceiverId())
        .message(requestChatLogDTO.getMessage())
        .channel(requestChatLogDTO.getChannel())
        .timestamp(requestChatLogDTO.getTimestamp())
        .chatType(requestChatLogDTO.getChatType())
        .build();
    chatLogRepository.save(chatLog);
    return requestChatLogDTO;
  }

  public List<ResponseChatLogDTO> getChatLogsBySenderId(Integer senderId) {
    List<ChatLog> list = chatLogRepository.getChatLogsBySenderId(senderId);
    List<ResponseChatLogDTO> result = list.stream()
        .map(c -> ResponseChatLogDTO.builder().
            senderId(c.getSender_id())
            .receiverId(c.getReceiver_id())
            .message(c.getMessage())
            .channel(c.getChannel())
            .timestamp(c.getTimestamp())
            .chatType(c.getChatType())
            .build()
        )
        .collect(Collectors.toList());
    return result;
  }
}
