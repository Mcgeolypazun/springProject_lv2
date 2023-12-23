package com.sparta.firsttask.controller;

import com.sparta.firsttask.dto.TodoPageDto;
import com.sparta.firsttask.dto.TodoRequestDto;
import com.sparta.firsttask.dto.TodoResponseDto;
import com.sparta.firsttask.entity.Todo;
import com.sparta.firsttask.repository.TodoRepository;
import com.sparta.firsttask.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

  private final TodoService todoService;

  private final TodoRepository todoRepository;


  @GetMapping("/{id}")
  public TodoResponseDto getTodoOne(@PathVariable Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 Todo가 존재하지 않습니다. id=" + id));

    return TodoResponseDto.of(todo);
  }

  @PutMapping("/{id}")
  public TodoResponseDto updateTodoOne(@PathVariable Long id,
      @RequestBody TodoRequestDto todoRequestDto) {
    return todoService.updateTodoOne(todoRequestDto, id);
  }

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoRequestDto todoRequestDto) {
    return todoService.todoCreate(todoRequestDto);
  }

  @GetMapping("/list")
  public TodoPageDto getTodoList(Pageable pageable) {
    return todoService.getTodoList(pageable);
  }

  @PutMapping("/check/{id}")
  public ResponseEntity<Void> checkTodo(@PathVariable Long id) {
    return todoService.checkTodo(id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
    return todoService.deleteTodo(id);
  }

}
