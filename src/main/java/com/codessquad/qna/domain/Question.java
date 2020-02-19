package com.codessquad.qna.domain;

import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long index;

  @Column(nullable = false)
  private String writer;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 500)
  private String contents;

//  @Column(nullable = false)
//  private LocalDateTime createdTime;


  public Long getIndex() {
    return index;
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

  public String getCreatedTime() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createdTime);
  }
//
//  public void setCreatedTime() {
//    this.createdTime = getCurrentTime();
//  }
//
//  private LocalDateTime getCurrentTime() {
//    return LocalDateTime.now();
//  }


  @Override
  public String toString() {
    return "writer : " + writer + '\n' +
        "title : " + title + '\n' +
        "contents : " + contents + '\n';
  }
}
