package com.codessquad.qna.repository;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Setter
    private String writer;

    @Column(nullable = false)
    @Setter
    private String title;

    @Column(nullable = false)
    @Setter
    private String contents;
    private LocalDate createdAt = LocalDate.now();

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }
}
