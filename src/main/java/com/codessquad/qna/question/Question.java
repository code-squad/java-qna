package com.codessquad.qna.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private long id;

    private int questionNumber;

    @Column(nullable = false, length = 20)
    private String writer;

    @Column(nullable = false, length = 25)
    private String title;

    private String contents;
    private String formattedWrittenTime;

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setFormattedWrittenTime(String formattedWrittenTime) {
        this.formattedWrittenTime = formattedWrittenTime;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedWrittenTime() {
        return formattedWrittenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", formattedWrittenTime='" + formattedWrittenTime + '\'' +
                '}';
    }
}
