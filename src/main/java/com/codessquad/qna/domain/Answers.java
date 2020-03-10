package com.codessquad.qna.domain;

import com.codessquad.qna.SpringContext;
import com.codessquad.qna.controller.posts.PostsRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Entity
public class Answers extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @ManyToOne
  @JoinColumn(name="AUTHOR_ID")
  private Users author;

  @ManyToOne
  @JoinColumn(name="POSTS_ID")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Posts posts;

  @Lob
  private String content;

  public Posts getPosts() {
    return posts;
  }

  public void setPosts(Posts posts) {
    this.posts = posts;
  }

  public void setAuthor(Users author) {
    this.author = author;
  }

  public void setId(Long id) {
    this.Id = id;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Answers(Users author, String content, Posts posts) {
    this.author = author;
    this.content = content;
    this.posts = posts;
  }

  public Answers() {
  }

  public static AnswersBuilder builder() {
    return new AnswersBuilder();
  }

  public void update(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Posts{" +
        "Id=" + Id +
        ", author='" + author + '\'' +
        ", content='" + content + '\'' +
        '}';
  }

  public Long getId() {
    return this.Id;
  }

  public Users getAuthor() {
    return this.author;
  }

  public String getContent() {
    return this.content;
  }

  public static class AnswersBuilder {

    private Users author;
    private String content;
    private Posts posts;

    AnswersBuilder() {
    }

    public static HttpSession getHttpSession() {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      return attr.getRequest().getSession(true);
    }

    public Answers.AnswersBuilder author() {
      HttpSession httpSession = getHttpSession();
      this.author = (Users) httpSession.getAttribute("sessionUser");
      return this;
    }

//    public Answers.AnswersBuilder author(Users author) {
//      this.author = author; //null
//      return this;
//    } 자바 객체를 JSON으로 변환하지 못해 발생하는 문제이다 seralize를 활용해보라는 디온의 조언이 있었으나 1차 시도 실패함.

    public Answers.AnswersBuilder question(Long postId) {
      PostsRepository postsRepository = SpringContext.getBean(PostsRepository.class);
      this.posts = postsRepository.findByPostId(postId);
      return this;
    }

    @Override
    public String toString() {
      return "AnswersBuilder{" +
          "author=" + author +
          ", content='" + content + '\'' +
          ", posts=" + posts +
          '}';
    }

    public Answers.AnswersBuilder content(String content) {
      this.content = content;
      return this;
    }

    public Answers build() {
      return new Answers(author, content, posts);
    }

  }
}