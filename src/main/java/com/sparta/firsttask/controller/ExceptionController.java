package com.sparta.firsttask.controller;

import com.sparta.firsttask.dto.MessageDto;
import com.sparta.firsttask.exception.NotFoundEntityException;
import jakarta.mail.SendFailedException;
import java.util.NoSuchElementException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler({SendFailedException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public MessageDto sendFail() {
    return new MessageDto("이메일 전송에 실패했습니다.");
  }

  @ExceptionHandler(NotFoundEntityException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public MessageDto notFound(NotFoundEntityException ex) {
    String message = ex.getMessage();
    if (message == null) {
      message = "존재하지 않습니다.";
    }
    return new MessageDto(message);
  }

  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public MessageDto notAllowed(AccessDeniedException ex) {
    return new MessageDto(ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public MessageDto handleNotFoundException(NotFoundException ex) {
    return new MessageDto(ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public MessageDto handleIllegalArgumentException(IllegalArgumentException ex) {
    String message = ex.getMessage();
    if (message == null) {
      message = "중복된 username 입니다.";
    }
    return new MessageDto(message);
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public MessageDto handleNoSuchElementException(NoSuchElementException ex) {
    String message = ex.getMessage();
    if (message == null) {
      message = "요청하신 프로필을 찾을 수 없습니다.";
    }
    return new MessageDto(message);
  }
}

