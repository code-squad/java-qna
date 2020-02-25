package com.codessquad.qna.domain;

import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Answer extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Question question;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
  private User writer;


  @Lob
  @Column(nullable = false)
  private String contents;

  public Answer() {
  }

  public Answer(Question question, User writer, String contents) {
    this.question = question;
    this.writer = writer;
    this.contents = contents;
  }

  public void update(String contents) {
    this.contents = contents;
  }

  public boolean isSameWriter(User sessionUser) {
    return writer.matchId(sessionUser.getId());
  }

  public Long getId() {
    return id;
  }

  public Answer setId(Long id) {
    this.id = id;
    return this;
  }

  public Question getQuestion() {
    return question;
  }

  public Answer setQuestion(Question question) {
    this.question = question;
    return this;
  }

  public String getWriter() {
    return writer.getUserId();
  }

  public Answer setWriter(User writer) {
    this.writer = writer;
    return this;
  }

  public String getContents() {
    return contents;
  }

  public Answer setContents(String contents) {
    this.contents = contents;
    return this;
  }

  public String getCreatedTime() {
    return getFormattedCreateTime();
  }

  private String getFormattedCreateTime() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createdTime);
  }

}
