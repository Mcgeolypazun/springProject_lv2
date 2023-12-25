package com.sparta.firsttask.jwt;

import com.sparta.firsttask.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;

  public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain filterChain) throws ServletException, IOException {

    String tokenValue = jwtUtil.getJwtFromHeader(req);

    if (StringUtils.hasText(tokenValue)) {
      try {
        if (!jwtUtil.validateToken(tokenValue)) {
          log.error("Token Error");
          res.setStatus(HttpStatus.UNAUTHORIZED.value());
          res.getWriter().write("유효하지 않은 JWT 토큰입니다.");
          return;
        }

        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        setAuthentication(info.getSubject());
      } catch (ExpiredJwtException e) {
        log.error("Expired JWT token, 만료된 JWT token 입니다.");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write("만료된 JWT 토큰입니다.");
        return;
      } catch (Exception e) {
        log.error(e.getMessage());
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write("JWT 토큰 처리 중 오류가 발생했습니다.");
        return;
      }
    }

    filterChain.doFilter(req, res);
  }

  // 인증 처리
  public void setAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  // 인증 객체 생성
  private Authentication createAuthentication(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }


}