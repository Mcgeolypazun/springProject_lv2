package com.sparta.firsttask.dto;

public record CommentCreateRequestDto(
        Long postId,
        Long userId,
        String content
) {

}
