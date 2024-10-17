package com.project.final_project.friendship.service;

import com.project.final_project.friendship.domain.Friendship;
import com.project.final_project.friendship.dto.GetFriendResponseDTO;
import com.project.final_project.user.domain.User;
import java.util.List;

public interface FriendshipService {
    void sendFriendRequest(Integer requesterId, Integer receiverId); // 친구 요청
    void acceptFriendRequest(Integer friendshipId); // 친구 수락
    void rejectFriendRequest(Integer friendshipId); // 친구 요청 거절
    List<Friendship> getAllFriendship(Boolean isAccepted); // isAccepted값에 따른 모든 Friendship 조회
    List<GetFriendResponseDTO> getFriends(Integer userId);
    Friendship findFriendshipByRequesterAndReceiver(User requester, User receiver);
}
