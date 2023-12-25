package com.sparta.firsttask.controller;

import com.sparta.firsttask.dto.MsgResponseDto;
import com.sparta.firsttask.dto.SignupRequestDto;
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

  @PostMapping("/signup")
  public ResponseEntity<MsgResponseDto> signup(
      @Valid @RequestBody SignupRequestDto userRequestDto) {
    userService.signup(userRequestDto);
    return ResponseEntity.ok(new MsgResponseDto("회원가입이 완료되었습니다.", HttpStatus.OK.value()));
  }


}

