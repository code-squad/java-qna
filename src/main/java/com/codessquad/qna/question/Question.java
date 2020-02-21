package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private int questionNumber;
    private String writer;
    private String title;
    private String contents;
    private String formattedWrittenTime;

    public Question(String writer, String title, String contents, String formattedWrittenTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.formattedWrittenTime = formattedWrittenTime;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
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
