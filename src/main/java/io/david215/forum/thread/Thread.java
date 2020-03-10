package io.david215.forum.thread;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Thread {
    private static int count = 0;

    private int id;
    private String author;
    private String title;
    private ZonedDateTime time;
    private String content;

    public Thread() {
        id = count++;
        time = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public String getFormattedTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getContent() {
        return content;
    }
}
