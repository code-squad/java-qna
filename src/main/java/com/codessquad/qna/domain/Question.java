package com.codessquad.qna.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {

  private int index;
  private String writer;
  private String title;
  private String contents;
  private LocalDateTime createdTime;

  public Question(String writer, String title, String contents) {
    this.index = 0;
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    this.createdTime = getCurrentTime();
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

  public String getTitle() {
    return title;
  }

  public String getContents() {
    return contents;
  }

  public String getCreatedTime() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createdTime);
  }

  private LocalDateTime getCurrentTime() {
    return LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "writer : " + writer + '\n' +
        "title : " + title + '\n' +
        "contents : " + contents + '\n' +
        "time : " + createdTime;
  }
}
