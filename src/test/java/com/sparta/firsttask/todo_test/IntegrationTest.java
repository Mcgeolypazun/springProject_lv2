//package com.sparta.firsttask.todo_test;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.firsttask.dto.JwtUser;
//import com.sparta.firsttask.entity.Comment;
//import com.sparta.firsttask.entity.Post;
//import com.sparta.firsttask.entity.User;
//import com.sparta.firsttask.entity.UserRole;
//import com.sparta.firsttask.entity.UserRoleEnum;
//import com.sparta.firsttask.jwt.JwtAuthenticationFilter;
//import com.sparta.firsttask.jwt.JwtUtil;
//import com.sparta.firsttask.repository.CommentRepository;
//import com.sparta.firsttask.repository.PostRepository;
//import com.sparta.firsttask.repository.UserRepository;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//@ActiveProfiles("test")
//public class IntegrationTest {
//
//  @Autowired
//  protected MockMvc mockMvc;
//  @Autowired
//  protected ObjectMapper mapper;
//  @Autowired
//  protected PostRepository postRepository;
//
//  @Autowired
//  protected CommentRepository commentRepository;
//  @Autowired
//  protected UserRepository userRepository;
//  @Autowired
//  protected JwtUtil jwtUtil;
//
//  protected Post savePost(String title, String content, User user) {
//    return postRepository.saveAndFlush(Post.builder()
//        .title(title)
//        .user(user)
//        .content(content)
//        .build()
//    );
//  }
//
//  protected User saveUser(String username, String password, String email, UserRoleEnum role) {
//    User user = User.builder()
//        .username(username)
//        .password(password)
//        .email(email)
//        .role(role)
//        .intro("intro")
//        .build();
//    return userRepository.saveAndFlush(user);
//  }
//
//  protected Comment saveComment(String content, User user, Post post) {
//    return commentRepository.saveAndFlush(Comment.builder()
//        .content(content)
//        .post(post)
//        .user(user)
//        .build()
//    );
//  }
//
//
//}