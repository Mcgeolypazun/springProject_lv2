package com.sparta.firsttask.dto;

import com.sparta.firsttask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
  private Long id;
  private String username;
  private String intro;
  private String email;


  // Profile User를 받아서 ProfileResponseDto로 변환하는 생성자 추가
  public ProfileResponseDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.intro = user.getIntro();
    this.email = user.getEmail();
  }
}