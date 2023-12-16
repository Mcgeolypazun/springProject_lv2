package com.sparta.firsttask.service;

import com.sparta.firsttask.Config.PasswordConfig;
import com.sparta.firsttask.dto.post.PostRequestDto;
import com.sparta.firsttask.dto.post.PostResponseDto;
import com.sparta.firsttask.dto.post.PostUpdateRequestDto;
import com.sparta.firsttask.entity.Post;
import com.sparta.firsttask.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        String password = passwordEncoder.encode(postRequestDto.password());
        Post post = Post.builder()
                .content(postRequestDto.content())
                .title(postRequestDto.title())
                .username(postRequestDto.username())
                .password(password)
                .build();
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
        return new PostResponseDto(post);
    }

    public ResponseEntity<?> updatePost(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
        if (!passwordEncoder.matches(postUpdateRequestDto.password(), post.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
        post.update(postUpdateRequestDto);
        postRepository.save(post);
        return ResponseEntity.ok().body("업데이트를 성공하였습니다.");

    }

    public ResponseEntity<?> deletePost(Long id, String password) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
        if (!passwordEncoder.matches(password, post.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
        postRepository.delete(post);
        return ResponseEntity.ok().body("비밀번호가 일치하여 삭제 합니다.");
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}
