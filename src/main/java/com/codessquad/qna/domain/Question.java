package com.codessquad.qna.domain;

import java.text.SimpleDateFormat;

public class Question {

  private int index;
  private String writer;
  private String title;
  private String contents;
  private String time;

  public Question(String writer, String title, String contents) {
    this.index = 0;
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    this.time = getCurrentTime();
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getTime() {
    return time;
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

  private String getCurrentTime() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis());
  }

  @Override
  public String toString() {
    return "writer : " + writer + '\n' +
        "title : " + title + '\n' +
        "contents : " + contents + '\n' +
        "time : " + time;
  }
}
