package com.sparta.firsttask.controller;

import com.sparta.firsttask.dto.post.PostRequestDto;
import com.sparta.firsttask.dto.post.PostResponseDto;
import com.sparta.firsttask.dto.post.PostUpdateRequestDto;
import com.sparta.firsttask.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping("/{id}")
  public PostResponseDto getPost(@PathVariable Long id) {
    return postService.getPost(id);
  }

  @GetMapping("/list")
  public ResponseEntity<List<PostResponseDto>> getPosts() {
    List<PostResponseDto> responseDto = postService.getPosts();
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping
  public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
    return postService.createPost(postRequestDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePost(@PathVariable Long id,
      @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
    return postService.updatePost(id, postUpdateRequestDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePost(@PathVariable Long id) {
    return postService.deletePost(id);
  }

}
