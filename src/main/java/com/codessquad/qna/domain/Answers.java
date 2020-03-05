package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpSession;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Entity
public class Answers extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @ManyToOne
  @JoinColumn(name = "AUTHOR_ID")
  private Users author;

  @ManyToOne
  @JoinColumn(name = "ANSWERS_POSTS")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Posts posts;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String title;

  @Lob
  private String content;

  public void setAuthor(Users author) {
    this.author = author;
  }

  public void setId(Long id) {
    Id = id;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Answers(Users author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
  }

  public Answers() {
  }

  public static AnswersBuilder builder() {
    return new AnswersBuilder();
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

  @Override
  public String toString() {
    return "Posts{" +
        "Id=" + Id +
        ", author='" + author + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }

  public Long getId() {
    return this.Id;
  }

  public Users getAuthor() {
    return this.author;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public static class AnswersBuilder {

    private Users author;
    private String title;
    private String content;

    AnswersBuilder() {
    }

    public static HttpSession getHttpSession() {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      return attr.getRequest().getSession(true);
    }

    public Answers.AnswersBuilder author() {
      HttpSession httpSession = getHttpSession();
      this.author = (Users) httpSession.getAttribute("sessionUser");
      System.out.println("sessionUser: " + this.author);
      return this;
    }

    public Answers.AnswersBuilder title(String title) {
      this.title = title;
      return this;
    }

    public Answers.AnswersBuilder content(String content) {
      this.content = content;
      return this;
    }

    public Answers build() {
      return new Answers(author, title, content);
    }

    public String toString() {
      return "Answers.AnswersBuilder(author=" + this.author + ", title=" + this.title + ", content="
          + this.content + ")";
    }
  }
}