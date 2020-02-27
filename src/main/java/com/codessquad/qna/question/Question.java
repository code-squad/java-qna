package com.codessquad.qna.question;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NotNull
public class Question {

  private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20)
  private String writer;
  private String title;
  private String contents;
  private LocalDateTime dateTime;

  public Question() {
    this.dateTime = LocalDateTime.now();
  }

  public String getDateTime() {
    return dateTime.format(dateTimeFormatter);
  }
}
