package com.codessquad.qna.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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

  @OneToMany(mappedBy = "posts")
  @OrderBy("Id asc")
  private List<Answers> answers;

  private boolean deleteStatus;

  public List<Answers> getAnswers() {
    return answers; //여기 자체에서 isDeleted = false 인 친구들만 꺼내게 할 수 없을까?
    //하려고 하면 'Basic' attribute type should not be a container 오류가 나타난다.
    //OneToMany를 설정하면 ArrayList는 설정이 안된다고 오류가 나타난다.
  }

  public ArrayList<Answers> getUndeletedAnswers() {
    ArrayList<Answers> UndeletedAnswers = new ArrayList<>();
    answers.stream().filter(answer -> !answer.isDeleted())
        .forEach(answer -> UndeletedAnswers.add(answer));
    return UndeletedAnswers;
  }

  public int getCountOfUndeletedAnswers() {
    return getUndeletedAnswers().size();
  }

  public void setAnswers(List<Answers> answers) {
    this.answers = answers;
  }

  @Override
  public String toString() {
    return "Posts{" +
        "Id=" + Id +
        ", author=" + author +
        ", answers=" + answers +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }

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
    this.deleteStatus = false;
  }

  public boolean isDeleted() {
    return deleteStatus;
  }

  public void deletePost(boolean deleteStatus) {
    this.deleteStatus = deleteStatus;
  }

  public long getCountOfAnswers() {
    return answers.stream().filter(answer -> !answer.isDeleted()).count();
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
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes();
      return attr.getRequest().getSession(true);
    }

    public Posts.PostsBuilder author() { //Posts 클래스 내부의 PostsBuilder 라는 의미
      HttpSession httpSession = getHttpSession();
      this.author = (Users) httpSession.getAttribute("sessionUser");
      System.out.println("sessionUser: " + this.author);
      return this;
    }

//    public Posts.PostsBuilder author(Users author) {
//      this.author = author;
//      return this;
//    }

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