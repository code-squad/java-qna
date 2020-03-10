package com.codessquad.qna.web.dto.posts;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.domain.Users;
import java.util.List;

public class PostsResponseDto {

  private List<Answers> answers;
  private Long id;
  private String title;
  private String content;
  private Users author;
  private int answers_length;

  public PostsResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.answers = entity.getAnswers();
    this.answers_length = entity.getAnswers().size();
  }

  public PostsResponseDto() {
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getAnswers_length() {
    return answers_length;
  }

  public void setAnswers_length(int answers_length) {
    this.answers_length = answers_length;
  }

  public String getContent() {
    return this.content;
  }

  public Users getAuthor() {
    return this.author;
  }

  public List<Answers> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answers> answers) {
    this.answers = answers;
  }
}