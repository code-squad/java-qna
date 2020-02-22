package com.codessquad.qna.domain;

import java.util.Date;

public class Question {

  private String writer;
  private String title;
  private String contents;
  private String time;
  private int index;

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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public void setTime() {
    Date time = new Date();
    this.time = time.toString();
  }

  @Override
  public String toString() {
    return "Question" + " title " + title + " writer " + writer + " contents " + contents;
  }
}