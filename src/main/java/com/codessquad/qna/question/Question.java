package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {

  private static int SEQ_NUM = 0;

  private int index;
  private String writer;
  private String title;
  private String contents;
  private String dateTime;

  public Question(LocalDateTime dateTime) {
    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    this.dateTime = localDateTime.format(dateTimeFormatter);
    index = ++SEQ_NUM;
  }


  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }
}
