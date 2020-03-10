package com.codessquad.qna;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionIndex;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;
    private String contents;
    private LocalDateTime writtenTime;

    public Question() {}

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writtenTime = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getWrittenTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String writtenTimeToString = writtenTime.format(dateTimeFormatter);
        return writtenTimeToString;
    }

    public Long getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(Long questionIndex) {
        this.questionIndex += questionIndex;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", now ='" + writtenTime + '\'' +
                '}';
    }
}
