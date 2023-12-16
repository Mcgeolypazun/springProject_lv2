package com.sparta.firsttask.dto.post;

public record PostUpdateRequestDto (
        String title,
        String content,
        String username,
        String password
){


}