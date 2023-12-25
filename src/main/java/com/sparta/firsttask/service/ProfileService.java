package com.sparta.firsttask.service;

import com.sparta.firsttask.dto.ProfileRequestDto;
import com.sparta.firsttask.dto.ProfileResponseDto;
import com.sparta.firsttask.entity.User;
import com.sparta.firsttask.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {

  private final UserRepository userRepository;

  @Transactional
  public ProfileResponseDto updateProfile(Long id, ProfileRequestDto requestDto) {
    User profileUser = userRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
    profileUser.update(requestDto);
    return new ProfileResponseDto(profileUser);
  }


  public ResponseEntity<?> getProfile(Long id) {
    try {
      // 해당 ID에 대한 프로필을 찾음
      User user = findProfile(id);
      // ProfileUser를 ProfileResponseDto로 변환하여 반환
      return ResponseEntity.ok(new ProfileResponseDto(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  private User findProfile(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("프로파일이 존재하지 않습니다."));
  }
}
