package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Entity
public class Posts extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @ManyToOne
  @JoinColumn(name = "AUTHOR_ID")
  private Users author;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String title;
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

  public Posts(Users author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
  }

  public Posts() {
  }

  public static PostsBuilder builder() {
    return new PostsBuilder();
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

  public static class PostsBuilder {

    private Users author;
    private String title;
    private String content;

    PostsBuilder() {
    }

    public static HttpSession getHttpSession() {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      return attr.getRequest().getSession(true);
    }

    public Posts.PostsBuilder author() { //Posts 클래스 내부의 PostsBuilder 라는 의미
      HttpSession httpSession = getHttpSession();
      this.author = (Users) httpSession.getAttribute("sessionUser");
      System.out.println("sessionUser: " + this.author);
      return this;
    }

    public Posts.PostsBuilder title(String title) {
      this.title = title;
      return this;
    }

    public Posts.PostsBuilder content(String content) {
      this.content = content;
      return this;
    }

    public Posts build() {
      return new Posts(author, title, content);
    }

    public String toString() {
      return "Posts.PostsBuilder(author=" + this.author + ", title=" + this.title + ", content="
          + this.content + ")";
    }
  }
}