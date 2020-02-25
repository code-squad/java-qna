package com.codessquad.qna.domain;

import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

@Entity
public class Question extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
  private User writer;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  @Type(type = "text")
  private String contents;

  public Question() {
  }

  public Question(User writer, String title, String contents) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
  }


  public Long getId() {
    return id;
  }

  public String getWriter() {
    return writer.getUserId();
  }

  public void setWriter(User writer) {
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
    return getFormattedCreateTime();
  }

  private String getFormattedCreateTime() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createdTime);
  }


  @Override
  public String toString() {
    return "writer : " + writer + '\n' +
        "title : " + title + '\n' +
        "contents : " + contents + '\n';
  }
}
