package com.sparta.firsttask.dto;

public record UpdateCommentRequest(
        Long id,
        String content
) {
}
