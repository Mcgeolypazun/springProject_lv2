package com.sparta.firsttask.entity;

import com.sparta.firsttask.dto.JwtUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String content;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime activatedAt;

  @Column
  private Boolean doComplete = false;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Builder
  public Todo(String title,String content, JwtUser user){
    this.title = title;
    this.content = content;
    this.user = User.foreign(user);
  }

  public void update(String title,String content){
    this.title = title;
    this.content = content;
  }

  public void check(){
    this.doComplete = true;
  }

}
