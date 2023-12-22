//package com.sparta.firsttask.todo_test;
//
//import static com.sparta.firsttask.entity.UserRoleEnum.USER;
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.firsttask.dto.JwtUser;
//import com.sparta.firsttask.dto.TodoRequestDto;
//import com.sparta.firsttask.entity.Post;
//import com.sparta.firsttask.entity.Todo;
//import com.sparta.firsttask.entity.User;
//import com.sparta.firsttask.entity.UserRoleEnum;
//import com.sparta.firsttask.jwt.JwtUtil;
//import com.sparta.firsttask.repository.TodoRepository;
//import com.sparta.firsttask.repository.UserRepository;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//@ExtendWith(MockitoExtension.class)
//@Transactional
//@DisplayName("할일 통합 테스트")
//public class TodoTest{
//
//
//  UserRepository userRepository;
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
//  User user;
//
//  Post post;
//  @Value("${jwt.accessToken.duration}")
//  long accessTokenTime;
//
//
//  private TodoRepository todoRepository;
//  @BeforeEach
//  void init() {
//    user = saveUser("한정석", "1234", "test@gmail.com", USER);
//  }
////  public TodoTest(TodoRepository todoRepository) {
////    this.todoRepository = todoRepository;
////  }
//
//  @Test
//  @DisplayName("할일 만들기")
//  public void testCreateTodo() throws Exception {
//
//
//    // Given
//
//    String title = "Test Todo";
//    String content = "Test Content";
//    TodoRequestDto todoRequestDto = new TodoRequestDto(title, content);
//    JwtUser jwtUser = JwtUser.of(user);
//    String token = jwtUtil.createToken(jwtUser, JwtUtil.ACCESS_TYPE);
//
//    // When
//    mockMvc.perform(
//            post("/api/v1/todos")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer " + token)
//                .content(mapper.writeValueAsString(todoRequestDto)))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.message").value("작성 성공!"))
//        .andExpect(jsonPath("$.code").value(200));
//
//    // Then
//    List<Todo> todoList = todoRepository.findAll();
//    assertThat(todoList).hasSize(1);
//    assertThat(todoList.get(0).getTitle()).isEqualTo(title);
//    assertThat(todoList.get(0).getContent()).isEqualTo(content);
//  }
//
//}
