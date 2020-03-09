package com.codessquad.qna.answer;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NotNull
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
  private Question question;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
  private User user;

  @Column(nullable = false)
  private String contents;
  @Column(nullable = false)
  private LocalDateTime createdDateTime;
  @Column(nullable = false)
  private LocalDateTime lastModifiedDateTime;

  public Answer() {
    this.createdDateTime = LocalDateTime.now();
    this.lastModifiedDateTime = LocalDateTime.now();
  }

  public String getLastModifiedDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return lastModifiedDateTime.format(dateTimeFormatter);
  }
}
