package com.codessquad.qna;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionIndex;

    @Column(nullable = false, length = 20)
    private String writer;

    @Column(nullable = false)
    private String title;
    private String contents;
    private String writtenTime;

    public Question() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        writtenTime = localDateTime.format(dateTimeFormatter);
        System.out.println(writtenTime);
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
        return writtenTime;
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
