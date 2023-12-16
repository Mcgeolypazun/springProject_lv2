package com.sparta.firsttask.controller;

import com.sparta.firsttask.dto.post.PostDeleteDto;
import com.sparta.firsttask.dto.post.PostRequestDto;
import com.sparta.firsttask.dto.post.PostResponseDto;
import com.sparta.firsttask.dto.post.PostUpdateRequestDto;
import com.sparta.firsttask.entity.Post;
import com.sparta.firsttask.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.updatePost(id,postUpdateRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,@RequestBody PostDeleteDto postDeleteDto){
        String password = postDeleteDto.getPassword();
        return  postService.deletePost(id, password);
    }

}
