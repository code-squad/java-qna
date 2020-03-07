package com.codessquad.qna.answer;

import com.codessquad.qna.question.Question;
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

  @Column(nullable = false, length = 20)
  private String userId;
  private String contents;
  private LocalDateTime createdDateTime;

  public Answer() {
    this.createdDateTime = LocalDateTime.now();
  }

  public String getCreatedDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return createdDateTime.format(dateTimeFormatter);
  }
}
