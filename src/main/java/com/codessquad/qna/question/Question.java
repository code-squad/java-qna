package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
public class Question {
    public static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern(CommonString.DATE_FORMAT);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    @OneToMany
    private Collection<Reply> replies;

    public Question() {}

    public Question(String writer, String title, String contents) {
        LocalDateTime now = LocalDateTime.now();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = now;
        this.updatedDateTime = now;
    }

    public long getId() {
        return id;
    }

    public void setId(long index) {
        this.id = index;
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
        return createdDateTime.format(DATE_TIME_FORMATTER);
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getFormattedUpdatedDateTime() {
        return updatedDateTime.format(DATE_TIME_FORMATTER);
    }

    public Collection<Reply> getReplies() {
        return replies;
    }

    public void setReplies(Collection<Reply> replies) {
        this.replies = replies;
    }

    public int getReplyCount() {
        return replies.size();
    }

}
