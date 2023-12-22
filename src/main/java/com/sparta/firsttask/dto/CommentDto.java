package com.sparta.firsttask.dto;

import com.sparta.firsttask.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        String content,
        String username,
        LocalDateTime createdAt
) {
    public static CommentDto of(Comment c) {
        return new CommentDto(c.getId(), c.getContent(), c.getUser().getUsername(), c.getCreatedAt());
    }
}
//
