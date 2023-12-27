package com.sparta.firsttask.service;

import com.sparta.firsttask.dto.JwtUser;
import com.sparta.firsttask.dto.post.PostRequestDto;
import com.sparta.firsttask.dto.post.PostResponseDto;
import com.sparta.firsttask.dto.post.PostUpdateRequestDto;
import com.sparta.firsttask.entity.Post;
import com.sparta.firsttask.entity.User;
import com.sparta.firsttask.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserStatusService userStatusService;

  public PostResponseDto createPost(PostRequestDto postRequestDto) {
    JwtUser loginUser = userStatusService.getLoginUser();
    User user = User.foreign(loginUser);
    Post post = Post.builder()
        .user(user)
        .content(postRequestDto.content())
        .title(postRequestDto.title())
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

    post.update(postUpdateRequestDto);
    postRepository.save(post);
    return ResponseEntity.ok().body("업데이트를 성공하였습니다.");

  }

  public ResponseEntity<?> deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    postRepository.delete(post);
    return ResponseEntity.ok().body("게시글을 삭제 합니다.");
  }

  public List<PostResponseDto> getPosts() {
    return postRepository.findAll().stream()
        .map(PostResponseDto::new)
        .collect(Collectors.toList());
  }
}
