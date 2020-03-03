package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Posts extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;


  @Column(columnDefinition = "TEXT", nullable = false)
  private String author;
  private String title;
  private String content;

  public Posts(String author, String title, String content) {
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

  public String getAuthor() {
    return this.author;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public static class PostsBuilder {

    private String author;
    private String title;
    private String content;

    PostsBuilder() {
    }

    public Posts.PostsBuilder author(String author) { //Posts 클래스 내부의 PostsBuilder 라는 의미
      this.author = author;
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