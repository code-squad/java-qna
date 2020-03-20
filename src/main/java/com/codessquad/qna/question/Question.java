package com.codessquad.qna.question;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

  @JsonIgnore
  @OneToMany(mappedBy = "question")
  private List<Answer> answers;

  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String contents;
  @Column(nullable = false)
  private LocalDateTime createdDateTime;
  @Column(nullable = false)
  private LocalDateTime lastModifiedDateTime;
  @Column(nullable = false)
  private boolean deleted;

  public Question() {
    this.createdDateTime = LocalDateTime.now();
    this.lastModifiedDateTime = LocalDateTime.now();
    this.deleted = false;
  }

  public void update(Question question) {
    this.title = question.title;
    this.contents = question.contents;
    this.lastModifiedDateTime = LocalDateTime.now();
  }

  public boolean checkAnswersWriter() {
    System.out.println("### checkAnswersWriter()");
    return this.answers.stream().allMatch(answer -> answer.getUser() == this.user);
  }

  public void delete() {
    this.deleted = true;
  }

  public String getLastModifiedDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return lastModifiedDateTime.format(dateTimeFormatter);
  }
}
