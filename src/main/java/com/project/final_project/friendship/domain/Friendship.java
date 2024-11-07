package com.project.final_project.friendship.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="friendship")
@Getter
@Setter
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "requester_id")
    private Integer requesterId;

    @Column(name = "receiver_id")
    private Integer receiverId;

    private boolean isAccepted;

    public Friendship() {}

    public Friendship(Integer requesterId, Integer receiverId, boolean isAccepted) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.isAccepted = isAccepted;
    }

    @Override
    public String toString() {
        return "Friendship{" +
            "id=" + id +
            ", requesterId=" + requesterId +
            ", receiverId=" + receiverId +
            ", isAccepted=" + isAccepted +
            '}';
    }
}
