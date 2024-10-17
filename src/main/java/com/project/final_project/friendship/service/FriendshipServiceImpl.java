package com.project.final_project.friendship.service;

import static java.util.stream.Collectors.*;

import com.project.final_project.friendship.domain.Friendship;
import com.project.final_project.friendship.dto.GetFriendResponseDTO;
import com.project.final_project.friendship.repository.FriendshipRepository;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService{

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Override
    public void sendFriendRequest(Integer requesterId, Integer receiverId) {
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new IllegalArgumentException("Requester not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Friendship foundFriendship = findFriendshipByRequesterAndReceiver(requester, receiver);

        if(foundFriendship != null) {
            if(foundFriendship.isAccepted()) {
                throw new IllegalStateException("이미 친구 관계 입니다.");
            }
            else {
                throw new IllegalStateException("이미 친구신청이 되었습니다.");
            }
        }

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setReceiver(receiver);
        friendship.setAccepted(false);

        friendshipRepository.save(friendship);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Integer friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        User requester = friendship.getRequester();
        User receiver = friendship.getReceiver();

        Friendship foundFriendship = findFriendshipByRequesterAndReceiver(receiver, requester);
        if(foundFriendship == null) {
            foundFriendship = new Friendship(receiver, requester, true);
            friendshipRepository.save(foundFriendship);
        }
        else {
            foundFriendship.setAccepted(true);
        }

        friendship.setAccepted(true);
        friendshipRepository.save(friendship);

    }

    @Override
    public void rejectFriendRequest(Integer friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        friendshipRepository.delete(friendship);
    }

    @Override
    public List<Friendship> getAllFriendship(Boolean isAccepted) {
        return friendshipRepository.findAllFriendship(isAccepted);
    }

    @Override
    public Friendship findFriendshipByRequesterAndReceiver(User requester, User receiver) {
        return friendshipRepository.findByRequesterIdAndReceiverId(requester.getId(), receiver.getId());
    }

    @Override
    public List<GetFriendResponseDTO> getFriends(Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Friendship> friendshipList = friendshipRepository.findByReceiverAndIsAccepted(user, true);
        List<User> friends = friendshipList.stream()
            .map(Friendship::getRequester)
            .toList();

      return friends.stream()
            .map(f -> GetFriendResponseDTO.builder()
                .name(f.getName())
                .age(f.getAge())
                .grade(f.getGrade())
                .birthday(f.getBirthday())
                .gender(f.getGender())
                .email(f.getEmail())
                .nickname(f.getNickname())
                .phone(f.getPhone())
                .level(f.getLevel())
                .exp(f.getExp())
                .maxExp(f.getMaxExp())
                .school(f.getSchool())
                .build()
            )
            .toList();
    }
}
