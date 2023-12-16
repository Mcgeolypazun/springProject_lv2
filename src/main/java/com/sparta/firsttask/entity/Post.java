package com.sparta.firsttask.entity;

import com.sparta.firsttask.dto.post.PostUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;// 숙련주차 삭제
    private String title;
    private String content;
    private String password;// 숙련주차 삭제


    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime activatedAt;
/*
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user  = user;
    }
 */// 숙련주차 주석 해제

    @Builder// 숙련주차 삭제
    private Post(String title, String content,String password,String username){
        this.title = title;
        this.content = content;
        this.password = password;
        this.username = username;
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.username = requestDto.username();
        this.title = requestDto.title();
        this.content = requestDto.content();
    }

}
