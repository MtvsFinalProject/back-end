package com.project.final_project.avatar.service;

import com.project.final_project.avatar.domain.Avatar;
import com.project.final_project.avatar.dto.AvatarRequestDTO;
import com.project.final_project.avatar.dto.AvatarResponseDTO;
import com.project.final_project.avatar.dto.AvatarUpdateDTO;
import com.project.final_project.avatar.repository.AvatarRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvatarService {

  private final AvatarRepository avatarRepository;

  public AvatarResponseDTO registerAvatar(AvatarRequestDTO avatarRequestDTO) {

    Avatar avatar = avatarRepository.getAvatarByUserId(avatarRequestDTO.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("not found user id : " + avatarRequestDTO.getUserId()));

    if(avatar != null){
      throw new IllegalStateException("이미 해당 유저의 아바타 정보가 등록되어있습니다.");
    }

    avatar = Avatar.builder()
        .userId(avatarRequestDTO.getUserId())
        .infoList(avatarRequestDTO.getInfoList())
        .build();

    Avatar savedAvatar = avatarRepository.save(avatar);
    return new AvatarResponseDTO(savedAvatar.getId(), savedAvatar.getInfoList());
  }

  public AvatarResponseDTO getAvatarByUserId(Integer userId) {
    Avatar foundAvatar = avatarRepository.getAvatarByUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("not found user id : " + userId));
    return new AvatarResponseDTO(foundAvatar.getId(), foundAvatar.getInfoList());
  }

  @Transactional
  public AvatarResponseDTO updateAvatar(AvatarUpdateDTO avatarUpdateDTO) {
    Integer id = avatarUpdateDTO.getUserId();
    Avatar foundAvatar = avatarRepository.getAvatarByUserId(id)
        .orElseThrow(() -> new IllegalArgumentException("not found user id : " + id));
    foundAvatar.setInfoList(avatarUpdateDTO.getInfoList());
    return new AvatarResponseDTO(foundAvatar.getId(), foundAvatar.getInfoList());
  }

  public void deleteAvatar(Integer userId) {
    avatarRepository.deleteAvatarByUserId(userId);
  }
}
