package com.codessquad.qna;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @NotEmpty
    private String title;

    @NotEmpty
    private String contents;
    private String postingTime;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        setPostingTime();
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

    public User getWriter() {
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

    public boolean isWriterEquals(String sessionUserId) {
        if (sessionUserId == null) {
            return false;
        }
        return sessionUserId.equals(writer);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "writer: " + writer + "\ntitle: " + title + "\ncontents: " + contents +
                "\npostingTime: " + postingTime + "\n";
    }
}
