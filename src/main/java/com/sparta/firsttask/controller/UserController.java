package com.sparta.firsttask.controller;

import static com.sparta.firsttask.jwt.JwtUtil.ACCESS_TYPE;
import static com.sparta.firsttask.jwt.JwtUtil.AUTHORIZATION_HEADER;
import static com.sparta.firsttask.jwt.JwtUtil.REFRESH_AUTHORIZATION_HEADER;
import static com.sparta.firsttask.jwt.JwtUtil.REFRESH_TYPE;

import com.sparta.firsttask.dto.JwtUser;
import com.sparta.firsttask.dto.LoginRequestDto;
import com.sparta.firsttask.dto.MsgResponseDto;
import com.sparta.firsttask.dto.SignupRequestDto;
import com.sparta.firsttask.jwt.JwtUtil;
import com.sparta.firsttask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  @PostMapping("/signup")
  public ResponseEntity<MsgResponseDto> signup(@Valid @RequestBody SignupRequestDto userRequestDto) {
    try {
      userService.signup(userRequestDto);
    } catch (IllegalArgumentException exception) {
      return ResponseEntity.badRequest().body(new MsgResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
    }

    return ResponseEntity.status(HttpStatus.CREATED.value())
        .body(new MsgResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
  }

//  @PostMapping("/login")
//  public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
//    try {
//      JwtUser user = userService.login(loginRequestDto);
////      String accessToken = jwtUtil.createToken(user, ACCESS_TYPE);
////      String refreshToken = jwtUtil.createToken(user, REFRESH_TYPE);
//
//      return ResponseEntity.ok()
////          .header(AUTHORIZATION_HEADER, accessToken)
////          .header(REFRESH_AUTHORIZATION_HEADER, refreshToken)
//          .body(new MsgResponseDto("로그인 성공", HttpStatus.OK.value()));
//
//    } catch (IllegalArgumentException e) {
//      return ResponseEntity.badRequest()
//          .body(new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
//    }
//
//  }
}

