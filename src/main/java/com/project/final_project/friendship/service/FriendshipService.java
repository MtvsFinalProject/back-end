package com.project.final_project.friendship.service;

import com.project.final_project.friendship.domain.Friendship;
import com.project.final_project.friendship.dto.FriendshipResponseDTO;
import com.project.final_project.friendship.repository.FriendshipRepository;
import com.project.final_project.user.domain.User;
import com.project.final_project.user.dto.UserDTO;
import com.project.final_project.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public void sendFriendRequest(Integer requesterId, Integer receiverId) {
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new IllegalArgumentException("Requester not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Friendship foundFriendshipByRequesterAndReceiver = findFriendshipByRequesterAndReceiver(requester, receiver);

        if(requesterId.equals(receiverId)) {
            throw new IllegalStateException("자기 자신한텐 친구 요청이 불가능합니다.");
        }

        if(foundFriendshipByRequesterAndReceiver != null) {
            if(foundFriendshipByRequesterAndReceiver.isAccepted()) {
                throw new IllegalStateException("이미 친구 관계 입니다.");
            }
            else {
                throw new IllegalStateException("이미 친구신청이 되었습니다.");
            }
        }

        Friendship friendship = new Friendship();
        friendship.setRequesterId(requesterId);
        friendship.setReceiverId(receiverId);
        friendship.setAccepted(false);

        friendshipRepository.save(friendship);
    }

    @Transactional
    public void acceptFriendRequest(Integer friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        Friendship foundFriendshipByRequesterAndReceiver =
            friendshipRepository.findByRequesterIdAndReceiverId(friendship.getRequesterId(), friendship.getReceiverId());

        Friendship foundFriendshipByReceiverAndRequester =
            friendshipRepository.findByRequesterIdAndReceiverId(friendship.getReceiverId(), friendship.getRequesterId());

        foundFriendshipByRequesterAndReceiver.setAccepted(true);

        // a가 b한테 요청하고, b가 a한테 요청한 후, 둘 중 하나가 수락이 되면 나머지 하나는 삭제
        if(foundFriendshipByReceiverAndRequester != null) {
            friendshipRepository.delete(foundFriendshipByReceiverAndRequester);
        }

        friendship.setAccepted(true);
        friendshipRepository.save(friendship);

    }

    @Transactional
    public void removeFriendship(Integer friendshipId) {
        friendshipRepository.deleteById(friendshipId);
    }

    public List<FriendshipResponseDTO> getAllAcceptedFriendships(Integer userId) {
        return friendshipRepository.getAllAcceptedFriendships(userId).stream()
            .map(f -> {
                User requester = userRepository.findById(f.getRequesterId()).orElseThrow(
                    ()  -> new IllegalArgumentException("not found user id : " + f.getRequesterId()));
                User receiver = userRepository.findById(f.getReceiverId()).orElseThrow(
                    () -> new IllegalArgumentException("not found user id : " + f.getReceiverId()));

                return new FriendshipResponseDTO(f.getId(), new UserDTO(requester), new UserDTO(receiver), f.isAccepted());
            })
            .toList();
    }

    public Friendship findFriendshipByRequesterAndReceiver(User requester, User receiver) {
        return friendshipRepository.findByRequesterIdAndReceiverId(requester.getId(), receiver.getId());
    }

    public List<FriendshipResponseDTO> getUnacceptedFriendshipListByReceiver(Integer receiverId) {
        return friendshipRepository.findByReceiverIdAndIsAccepted(receiverId, false).stream()
            .map(f -> {
                User requester = userRepository.findById(f.getRequesterId()).orElseThrow(
                    () -> new IllegalArgumentException("not found user id : " + f.getRequesterId()));

                User receiver = userRepository.findById(receiverId).orElseThrow(
                    () -> new IllegalArgumentException("not found user id : " + receiverId));

                return new FriendshipResponseDTO(f.getId(), new UserDTO(requester), new UserDTO(receiver), f.isAccepted());
            })
            .toList();
    }

    public List<FriendshipResponseDTO> getUnacceptedFriendshipListByRequester(Integer requesterId) {
        return friendshipRepository.findByRequesterIdAndIsAccepted(requesterId, false).stream()
            .map(f -> {
                User requester = userRepository.findById(requesterId).orElseThrow(
                    () -> new IllegalArgumentException("not found user id : " + requesterId));

                User receiver = userRepository.findById(f.getReceiverId()).orElseThrow(
                    () -> new IllegalArgumentException("not found user id : " + f.getReceiverId()));

                return new FriendshipResponseDTO(f.getId(), new UserDTO(requester), new UserDTO(receiver), f.isAccepted());
            })
            .toList();
    }
}
