package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Question {
    public static final String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss";
    private int index;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDateTime;
    private List<Object> replies;

    public Question(int index, String writer, String title, String contents) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
        this.replies = new ArrayList<>();
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
        return createdDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public List<Object> getReplies() {
        return replies;
    }

    public void setReplies(List<Object> replies) {
        this.replies = replies;
    }

    public int getReplyCount() {
        return replies.size();
    }

}
