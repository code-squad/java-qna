package codesquad;

import java.time.LocalDateTime;

public class Question {
    private String author;
    private String title;
    private String content;
    private String date;

    public Question() {
        LocalDateTime timeStamp = LocalDateTime.now();
        this.date = timeStamp.toString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }
}
