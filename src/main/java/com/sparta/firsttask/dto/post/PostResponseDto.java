package com.sparta.firsttask.dto.post;

import com.sparta.firsttask.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime activatedAt;


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.activatedAt = post.getActivatedAt();
        this.username = post.getUsername();
        //this.author = post.getUser().getUsername();
    }
}