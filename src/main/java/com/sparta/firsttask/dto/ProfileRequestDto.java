package com.sparta.firsttask.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class ProfileRequestDto {
    private String title;
    private String content;
    private String intro;
    private String username;
    private String password;
}
