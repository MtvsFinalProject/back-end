package com.project.final_project.chatlog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
//@RedisHash(value = "chatlog", timeToLive = 300)
public class ChatLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer sender_id;
  private Integer receiver_id;
  private String message;
  private String timestamp;
  private String channel;
  private ChatType chatType;
}
