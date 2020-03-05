package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NotNull
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String userId;
  private String title;
  private String contents;
  private LocalDateTime createdDateTime;

  public Question() {
    this.createdDateTime = LocalDateTime.now();
  }

  public void update(Question question) {
    this.userId = question.userId;
    this.title = question.title;
    this.contents = question.contents;
    this.createdDateTime = question.createdDateTime;
  }

  public boolean validateUserId(User user) {
    return this.userId.equals(user.getUserId());
  }

  public String getCreatedDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return createdDateTime.format(dateTimeFormatter);
  }
}
