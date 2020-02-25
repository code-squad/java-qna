package com.codessquad.qna.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Posts extends BaseTimeEntity {
  @Id
  private Long id;

  private String author;
  private String title;
  private String content;
  private LocalDateTime createdTime;

  @Override
  public String toString() {
    return "Posts{" +
        "id=" + id +
        ", author='" + author + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", createdTime=" + createdTime +
        '}';
  }
}