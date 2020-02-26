package com.codessquad.qna;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @Min(1)
    private int id;

    @NotNull
    @Size(min = 1, max = 10)
    private String writer;

    @NotNull
    @Size(min = 1, max = 10)
    private String title;

    @NotNull
    @Size(min = 1, max = 100)
    private String contents;

    private LocalDateTime currentTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public String getFormattedTime() {
        return currentTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
