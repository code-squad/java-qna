package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {

  private static int SEQ_NUM = 0;

  private String index;
  private String writer;
  private String title;
  private String contents;
  private String dateTime;

  public Question(LocalDateTime dateTime) {
    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    this.dateTime = localDateTime.format(dateTimeFormatter);
    index = (++SEQ_NUM) + "";
  }

  @Override
  public String toString() {
    return "Question{" +
        "index='" + index + '\'' +
        ", writer='" + writer + '\'' +
        ", title='" + title + '\'' +
        ", contents='" + contents + '\'' +
        ", dateTime='" + dateTime + '\'' +
        '}';
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
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
