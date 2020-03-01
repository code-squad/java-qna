package com.codessquad.qna.repository;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    @Setter
    private String title;

    @Column(nullable = false)
    @Setter
    private String contents;
    private LocalDate createdAt = LocalDate.now();

    public Question () {}
    public Question(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.writer = user;
    }

    public void update(Question question) {
        this.title = question.title;
        this.contents = question.contents;
    }

    public boolean isCorrectFormat(Question question) {
        boolean titleIsExist = ObjectUtils.isNotEmpty(question.getTitle());
        boolean contentIsExist = ObjectUtils.isNotEmpty(question.getContents());
        boolean writerIsExist = ObjectUtils.isNotEmpty(question.getWriter());
        return titleIsExist && contentIsExist && writerIsExist;
    }

    public boolean isCorrectWriter(User user) {
        return this.writer.getId().equals(user.getId());
    }
}
