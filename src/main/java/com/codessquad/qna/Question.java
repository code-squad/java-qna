package com.codessquad.qna;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private long questionIndex;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime dateTime;
    private int replyCount;

    public Question(String writer, String title, String contents) {
        this.questionIndex = 0;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.dateTime = LocalDateTime.now();
        this.replyCount = 0;
    }

    public long getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(long questionIndex) {
        this.questionIndex = questionIndex;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormattedDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionIndex=" + questionIndex +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", dateTime=" + dateTime +
                ", replyCount=" + replyCount +
                '}';
    }
}
