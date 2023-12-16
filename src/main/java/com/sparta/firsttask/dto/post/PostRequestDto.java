package com.sparta.firsttask.dto.post;

import lombok.Getter;


public record PostRequestDto (
         String title,
        String content,
         String username,
         String password
){
}
