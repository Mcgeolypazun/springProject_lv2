package com.sparta.firsttask.service;

import com.sparta.firsttask.dto.JwtUser;
import com.sparta.firsttask.dto.MsgResponseDto;
import com.sparta.firsttask.dto.TodoPageDto;
import com.sparta.firsttask.dto.TodoRequestDto;
import com.sparta.firsttask.dto.TodoResponseDto;
import com.sparta.firsttask.entity.Todo;
import com.sparta.firsttask.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserStatusService userStatusService;

  public ResponseEntity<?> todoCreate(TodoRequestDto todoRequestDto) {
    JwtUser loginUser = userStatusService.getLoginUser();
    //User user = User.foreign(loginUser);

    Todo todo = new Todo(todoRequestDto.getTitle(), todoRequestDto.getContent(), loginUser);
    todoRepository.save(todo);

    return ResponseEntity.status(HttpStatus.OK).body(new MsgResponseDto("작성 성공!", 200));
  }

  public TodoResponseDto updateTodoOne(TodoRequestDto todoRequestDto, Long id) {
    Optional<Todo> todo = todoRepository.findById(id);

    todo.ifPresent(todoUpdate -> {
      todoUpdate.update(todoRequestDto.getTitle(), todoRequestDto.getContent());
      todoRepository.save(todoUpdate); // 변경된 내용을 데이터베이스에 저장합니다.
    });

    return todo.map(TodoResponseDto::of).orElse(null);

  }

  public TodoPageDto getTodoList(Pageable pageable) {
    Page<Todo> result = todoRepository.findAll(pageable);
    	/**
       * * Returns the page content as {@link List}.
	     *
	     * @return
       * List<T> getContent();
       */
    var data = result.getContent().stream()
        .map(TodoResponseDto::of)
        .toList();

    return new TodoPageDto(data,
        result.getTotalElements(),
        result.getTotalPages(),
        pageable.getPageNumber(),
        data.size()
        );
  }

  public ResponseEntity<Void> checkTodo(Long id) {
    Optional<Todo> optionalTodo = todoRepository.findById(id);
    if (optionalTodo.isPresent()) {
      Todo todo = optionalTodo.get();
      todo.check();
      todoRepository.save(todo);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<String> deleteTodo(Long id){
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("없는 id 입니다."));
    todoRepository.delete(todo);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
