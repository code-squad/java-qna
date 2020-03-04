package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
  @JsonProperty
  private User writer;

  @Column(nullable = false)
  @JsonProperty
  private String title;

  @Lob
  @Column(nullable = false)
  @JsonProperty
  private String contents;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
  @OrderBy(value = "id ASC")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonIgnore
  private List<Answer> answerList;

  @JsonProperty
  private Integer countOfAnswer;

  public Question() {
  }

  public Question(User writer, String title, String contents) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    this.countOfAnswer = 0;
  }

  public void update(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public boolean isSameWriter(User sessionUser) {
    return writer.matchId(sessionUser.getId());
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

  public List<Answer> getAnswerList() {
    return answerList;
  }

  public Question setAnswerList(List<Answer> answerList) {
    this.answerList = answerList;
    return this;
  }

  public void addAnswer() {
    this.countOfAnswer++;
  }

  public void deleteAnswer() {
    this.countOfAnswer--;
    if(countOfAnswer < 0) {
      countOfAnswer = 0;
    }
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

  public Integer getCountOfAnswer() {
    return countOfAnswer;
  }

  public Question setCountOfAnswer(Integer countOfAnswer) {
    this.countOfAnswer = countOfAnswer;
    return this;
  }

  @Override
  public String toString() {
    return "writer : " + writer + '\n' +
        "title : " + title + '\n' +
        "contents : " + contents + '\n';
  }
}
