package com.sparta.firsttask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}$", message = "username은 알파벳 소문자(a~z), 숫자(0~9) 4~10자리로 구성되어야 합니다.")
        String username;

        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}", message = "password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 8~15자로 구성되어야 합니다.")
        String password;

        private String intro;

        @Email
        @NotBlank
        private String email;
        private boolean admin = false;
        private String adminToken = "";
}