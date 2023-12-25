package com.sparta.firsttask.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.sparta.firsttask.entity.UserRoleEnum;
import com.sparta.firsttask.jwt.JwtAuthenticationFilter;
import com.sparta.firsttask.jwt.JwtAuthorizationFilter;
import com.sparta.firsttask.jwt.JwtUtil;
import com.sparta.firsttask.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
    filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
    return filter;
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // CSRF 설정
    http.csrf((csrf) -> csrf.disable());

    // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
    http.sessionManagement((sessionManagement) ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    );

    http.authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            .permitAll() // resources 접근 허용 설정
            .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
            .requestMatchers(
                antMatcher(POST, "/api/v1/login"),
                antMatcher(POST, "/api/v1/signup"),
                antMatcher(POST, "/api/v1/signup/email"),
                antMatcher(GET, "/api/v1/posts"),
                antMatcher(GET, "/api/v1/post/**"),
                antMatcher(GET, "/api/v1/refresh**"),
                antMatcher(GET, "/api/v1/comment/**"),
                antMatcher(GET, "/api/v1/todo/**"),
                antMatcher(GET, "/api/v1/todo/list")
            ).permitAll()
            .requestMatchers(
                antMatcher(GET,"/api/v1/todo/**"),
                antMatcher(POST, "/api/v1/todo"),
                antMatcher(PUT,"/api/v1/todo/**"),
                antMatcher(GET,"/api/v1/todo/list"),
                antMatcher(GET,"/api/v1/todo/list/**"),
                antMatcher(PUT,"/api/v1/todo/check/**"),
                antMatcher(DELETE,"/api/v1/todo/**"),
                antMatcher(DELETE,"/api/v1/signout"),
                antMatcher(GET,"/api/v1/refresh"),//authcontroller, TodoController까지 함
                antMatcher(POST,"/api/v1/comment"),
                antMatcher(DELETE,"/api/v1/comment/**"),
                antMatcher(PATCH,"/api/v1/comment"), // commentController
                antMatcher(POST, "/api/v1/post"),
                antMatcher(PUT, "/api/v1/post/**"),
                antMatcher(DELETE,"/api/v1/post/**"),//postController
                antMatcher(PATCH,"/api/v1/user/**")//profileController
            ).hasRole(String.valueOf(UserRoleEnum.USER))
            .anyRequest().hasAuthority(UserRoleEnum.ADMIN.getAuthority()) // 그 외 모든 요청 인증처리 백오피스 구현하기
    );

//    http.formLogin((formLogin) ->
//        formLogin
//            .loginPage("/api/user/login-page").permitAll()
//    );

    // 필터 관리
    http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}

