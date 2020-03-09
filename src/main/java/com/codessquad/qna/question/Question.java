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

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_user"))
  private User user;

  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String contents;
  @Column(nullable = false)
  private LocalDateTime createdDateTime;

  public Question() {
    this.createdDateTime = LocalDateTime.now();
  }

  public void update(Question question) {
    this.user = question.user;
    this.title = question.title;
    this.contents = question.contents;
    this.createdDateTime = question.createdDateTime;
  }

  public String getCreatedDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return createdDateTime.format(dateTimeFormatter);
  }
}
