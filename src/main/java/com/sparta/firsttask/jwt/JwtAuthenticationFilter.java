package com.sparta.firsttask.jwt;

import static com.sparta.firsttask.jwt.JwtUtil.ACCESS_TYPE;
import static com.sparta.firsttask.jwt.JwtUtil.REFRESH_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.firsttask.dto.JwtUser;
import com.sparta.firsttask.dto.LoginRequestDto;
import com.sparta.firsttask.entity.User;
import com.sparta.firsttask.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    setFilterProcessesUrl("/api/v1/login");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          LoginRequestDto.class);
      //json 형태의 데이터를 loginRequestDto로 매핑함
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getUsername(),
              requestDto.getPassword(),
              null
              //ㄴ 사용자의 권한 목록을 나타낸다. 일반적으로는 null이 들어가며,
              // 인증 후 권한이 부여된 경우 이 부분이 채워질 수 있다.
          )
      );
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) {

    User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
    String accessToken = jwtUtil.createToken(JwtUser.of(user), ACCESS_TYPE);
    String refreshToken = jwtUtil.createToken(JwtUser.of(user), REFRESH_TYPE);

    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
    response.addHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER, refreshToken);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) {
    response.setStatus(401);
  }



}