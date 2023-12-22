package com.sparta.firsttask.controller;


import com.sparta.firsttask.dto.CommentCreateRequestDto;
import com.sparta.firsttask.dto.CommentDto;
import com.sparta.firsttask.dto.MessageDto;
import com.sparta.firsttask.dto.PageDto;
import com.sparta.firsttask.dto.UpdateCommentRequest;
import com.sparta.firsttask.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<?> createComment(@RequestBody CommentCreateRequestDto request) {
    CommentDto response = commentService.createComment(request);
    return ResponseEntity.ok(response);
  }

  @PatchMapping
  public ResponseEntity<?> updateComment(@RequestBody UpdateCommentRequest request) {
    CommentDto response = commentService.updateComment(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<?> getComments(@PathVariable Long postId, Pageable pageable) {
    PageDto response = commentService.getComments(pageable, postId);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
    return ResponseEntity.ok(new MessageDto("댓글 삭제 성공"));
  }
}
