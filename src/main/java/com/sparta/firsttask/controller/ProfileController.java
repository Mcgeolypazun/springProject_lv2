package com.sparta.firsttask.controller;


import com.sparta.firsttask.dto.JwtUser;
import com.sparta.firsttask.dto.MessageDto;
import com.sparta.firsttask.dto.ProfileAndTokenResponseDto;
import com.sparta.firsttask.dto.ProfileRequestDto;
import com.sparta.firsttask.dto.ProfileResponseDto;
import com.sparta.firsttask.entity.User;
import com.sparta.firsttask.jwt.JwtUtil;
import com.sparta.firsttask.repository.UserRepository;
import com.sparta.firsttask.security.UserDetailsImpl;
import com.sparta.firsttask.service.ProfileService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class ProfileController {

  private final ProfileService profileService;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;



  @GetMapping("/{id}")
  public ResponseEntity<?> getProfileById(@PathVariable Long id) {
    return profileService.getProfile(id);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDto requestDto) {
    try {
      ProfileResponseDto profile = profileService.updateProfile(id, requestDto);

      // 데이터베이스에서 업데이트된 유저 정보를 가져옵니다.
      User updatedUser = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
      JwtUser updatedJwtUser = JwtUser.of(updatedUser);

      String accessToken = jwtUtil.createToken(updatedJwtUser, JwtUtil.ACCESS_TYPE);
      String refreshToken = jwtUtil.createToken(updatedJwtUser, JwtUtil.REFRESH_TYPE);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("Access-Token", accessToken);
      responseHeaders.set("Refresh-Token", refreshToken);

      ProfileAndTokenResponseDto response = new ProfileAndTokenResponseDto();
      response.setProfile(profile);
//      response.setAccessToken(accessToken);
//      response.setRefreshToken(refreshToken);

      return ResponseEntity.ok().headers(responseHeaders).body(response);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new MessageDto("Failed to update profile"));
    }
  }



}