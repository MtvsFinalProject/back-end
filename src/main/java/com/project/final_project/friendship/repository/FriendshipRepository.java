package com.project.final_project.friendship.repository;

import com.project.final_project.friendship.domain.Friendship;
import com.project.final_project.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    List<Friendship> findByRequesterAndIsAccepted(User requester, boolean isAccepted);
    List<Friendship> findByReceiverAndIsAccepted(User requester, boolean isAccepted);
    @Query("SELECT f FROM Friendship f WHERE f.isAccepted = :isAccepted")
    List<Friendship> findAllFriendship(@Param("isAccepted") Boolean isAccepted);
    @Query("select f from Friendship f where f.requester.id = :requesterId and f.receiver.id = :receiverId")
    Friendship findByRequesterIdAndReceiverId(@Param("requesterId") Integer requesterId, @Param("receiverId") Integer receiverId);}
