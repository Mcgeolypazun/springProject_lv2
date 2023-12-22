package com.sparta.firsttask.dto;

import com.sparta.firsttask.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDto {
  String title;
  String content;

  public TodoResponseDto(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public static TodoResponseDto of(Todo todo) {
    return new TodoResponseDto(todo.getTitle(),todo.getContent());
  }
}
