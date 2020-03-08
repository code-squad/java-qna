package com.codessquad.qna;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long answerNumber;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    private Long relatedPostNumber;

    public Answer() {}

    public Answer(User writer, Question question, String contents, Long relatedPostNumber) {
        this.relatedPostNumber = relatedPostNumber;
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return answerNumber.equals(answer.answerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerNumber);
    }

    @Override
    public String toString() {
        return "Answer [" +
                "answerNumber=" + answerNumber +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                ", relatedPostNumber =" + relatedPostNumber
                ;
    }

    public Long getAnswerNumber() {
        return answerNumber;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Long getRelatedPostNumber() {
        return relatedPostNumber;
    }
}
