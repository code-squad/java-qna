package com.codessquad.qna;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String writer;

    @NotEmpty
    private String title;

    @NotEmpty
    private String contents;
    private String postingTime;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public Question() {
        setPostingTime();
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

    public void setPostingTime() {
        this.postingTime = getLocalDateTime();
    }

    public Long getId() { return id; }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getPostingTime() {
        return postingTime;
    }

    private String getLocalDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Override
    public String toString() {
        return "index: " + id + "\nwriter: " + writer + "\ntitle: " + title + "\ncontents: " + contents +
                "\npostingTime: " + postingTime + "\n";
    }
}
