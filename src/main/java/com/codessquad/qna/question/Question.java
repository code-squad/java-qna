package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private int index;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDateTime;
    private int replyCount;

    public Question(int index, String writer, String title, String contents) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
        this.replyCount = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getFormattedCreatedDateTime() {
        return createdDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
