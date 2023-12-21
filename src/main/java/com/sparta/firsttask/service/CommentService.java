package com.sparta.firsttask.service;

import com.sparta.firsttask.dto.*;
import com.sparta.firsttask.entity.Comment;
import com.sparta.firsttask.dto.PageDto;
import com.sparta.firsttask.entity.Post;
import com.sparta.firsttask.entity.User;
import com.sparta.firsttask.exception.NotFoundEntityException;
import com.sparta.firsttask.repository.CommentRepository;
import com.sparta.firsttask.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserStatusService userStatusManager;
    private final PostRepository postRepository;

    public CommentDto createComment(CommentCreateRequestDto req) {
        var loginUser = userStatusManager.getLoginUser();

        if(!postRepository.existsById(req.postId())){
            throw new NotFoundEntityException("존재하지 않은 게시물입니다.");
        }

        Comment comment = commentRepository.saveAndFlush(Comment.builder()
                .content(req.content())
                .user(User.foreign(loginUser))
                .post(Post.foreign(req.postId()))
                .build());
        return CommentDto.of(comment);
    }

    public CommentDto updateComment(UpdateCommentRequest req) {
        var loginUser = userStatusManager.getLoginUser();

        Comment comment = commentRepository.findById(req.id())
                .orElseThrow(NotFoundEntityException::new);

        if (!comment.getUser().getId().equals(loginUser.id())) {
            throw new AccessDeniedException("댓글 수정 권한이 없습니다.");
        }
        comment.update(req);
        return CommentDto.of(comment);
    }

    public void deleteComment(Long id) {
        var loginUser = userStatusManager.getLoginUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundEntityException::new);

        if (!comment.getUser().getId().equals(loginUser.id())) {
            throw new AccessDeniedException("댓글 삭제 권한이 없습니다.");
        }
        commentRepository.deleteById(id);
    }

    public PageDto getComments(Pageable pageable, Long postId) {
        Page<Comment> result = commentRepository.findByPostId(postId, pageable);
        var data = result.getContent().stream()
                .map(CommentDto::of)
                .toList();

        return new PageDto(data,
                result.getTotalElements(),
                result.getTotalPages(),
                pageable.getPageNumber(),
                data.size()
        );

    }
}
