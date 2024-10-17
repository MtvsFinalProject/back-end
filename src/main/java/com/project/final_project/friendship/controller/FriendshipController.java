package com.project.final_project.friendship.controller;

import com.project.final_project.friendship.dto.GetFriendResponseDTO;
import com.project.final_project.friendship.service.FriendshipServiceImpl;
import com.project.final_project.user.domain.User;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipServiceImpl friendshipServiceImpl;

    @GetMapping("/list")
    public ResponseEntity<List<GetFriendResponseDTO>> getFriendships(@RequestParam("userId") Integer userId) {
        List<GetFriendResponseDTO> friends = friendshipServiceImpl.getFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "409", description = "이미 친구 신청이 되었습니다.")
    })
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam Integer requesterId, @RequestParam Integer receiverId) {
        friendshipServiceImpl.sendFriendRequest(requesterId, receiverId);
        return ResponseEntity.ok("친구 요청을 보냈습니다.");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Integer friendshipId) {
        friendshipServiceImpl.acceptFriendRequest(friendshipId);
        return ResponseEntity.ok("친구 요청을 수락했습니다.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam Integer friendshipId) {
        friendshipServiceImpl.rejectFriendRequest(friendshipId);
        return ResponseEntity.ok("친구 요청을 거절했습니다.");
    }


}
