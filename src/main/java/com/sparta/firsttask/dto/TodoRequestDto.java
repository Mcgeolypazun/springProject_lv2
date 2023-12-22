package com.sparta.firsttask.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
@AllArgsConstructor
public class TodoRequestDto {
  String title;
  String content;
}
