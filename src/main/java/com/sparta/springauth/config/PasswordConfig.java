package com.sparta.springauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration//스프링 ㅓ버가 뜰때 ioc 컨테이너에 포함될것 소문자 passwordConfig로 저장됨
public class PasswordConfig {

    @Bean//
    public PasswordEncoder passwordEncoder() {//passwordEncoder로 저장되어 가져다 쓸 수 있다.
        return new BCryptPasswordEncoder();//인터페이스임 구현체 사용 DI하면 구현체가 등록됨 hash함수임 비밀번호를 등록해줌
    }
}