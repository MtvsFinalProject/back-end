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
@Setter
//@RedisHash(value = "chatlog", timeToLive = 300)
public class ChatLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer senderId;
  private Integer receiverId;
  private String message;
  private String timestamp;
  private String channel;
  private ChatType chatType;

  @Override
  public String toString() {
    return "ChatLog{" +
        "id=" + id +
        ", senderId=" + senderId +
        ", receiverId=" + receiverId +
        ", message='" + message + '\'' +
        ", timestamp='" + timestamp + '\'' +
        ", channel='" + channel + '\'' +
        ", chatType=" + chatType +
        '}';
  }
}
