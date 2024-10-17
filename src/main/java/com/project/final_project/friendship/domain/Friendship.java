package com.project.final_project.friendship.domain;

import com.project.final_project.user.domain.User;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private boolean isAccepted;

    public Friendship() {}

    public Friendship(User requester, User receiver, boolean isAccepted) {
        this.requester = requester;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", requester=" + requester +
                ", receiver=" + receiver +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
