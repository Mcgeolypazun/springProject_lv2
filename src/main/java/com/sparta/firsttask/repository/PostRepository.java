package com.sparta.firsttask.repository;

import com.sparta.firsttask.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "post")
    Optional<Post> findFetchJoinUserById(Long id);

}
