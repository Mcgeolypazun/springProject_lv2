package com.sparta.firsttask.controller;

import static com.sparta.firsttask.jwt.JwtUtil.ACCESS_TYPE;
import static com.sparta.firsttask.jwt.JwtUtil.AUTHORIZATION_HEADER;
import static com.sparta.firsttask.jwt.JwtUtil.REFRESH_AUTHORIZATION_HEADER;
import static com.sparta.firsttask.jwt.JwtUtil.REFRESH_TYPE;

import com.sparta.firsttask.dto.JwtUser;
import com.sparta.firsttask.dto.MessageDto;
import com.sparta.firsttask.jwt.JwtUtil;
import com.sparta.firsttask.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {


  private final JwtUtil jwtUtil;
  private final UserService userService;

//  @Value("${email.auth.subject}")
//  private String EMAIL_AUTH_SUBJECT;


  @DeleteMapping("/signout")
  public ResponseEntity<?> signOut() {
    userService.signOut();
    return ResponseEntity.ok(new MessageDto("탈퇴했습니다."));
  }

  @GetMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestHeader(value = AUTHORIZATION_HEADER, required = false) String token) {

    Optional<JwtUser> bearerToken = jwtUtil.getJwtUser(token, REFRESH_TYPE);
    if (bearerToken.isEmpty()) {
      return ResponseEntity.badRequest().body(new MessageDto("토큰이 유효하지 않습니다."));
    }

    JwtUser user = bearerToken.get();

    String accessToken = jwtUtil.createToken(user, ACCESS_TYPE);
    String refreshToken = jwtUtil.createToken(user, REFRESH_TYPE);

    return ResponseEntity.ok()
        .header(AUTHORIZATION_HEADER, accessToken)
        .header(REFRESH_AUTHORIZATION_HEADER, refreshToken)
        .build();
  }

}
