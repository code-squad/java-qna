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
  @Column(nullable = false)
  private LocalDateTime lastModifiedDateTime;

  public Question() {
    this.createdDateTime = LocalDateTime.now();
    this.lastModifiedDateTime = LocalDateTime.now();
  }

  public void update(Question question) {
    this.title = question.title;
    this.contents = question.contents;
    this.lastModifiedDateTime = LocalDateTime.now();
  }

  public String getLastModifiedDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return lastModifiedDateTime.format(dateTimeFormatter);
  }
}
