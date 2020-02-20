package com.codessquad.qna.question;

import com.codessquad.qna.common.Common;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;

    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @OneToMany
    private Collection<Reply> replies;

    public Question() {
    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
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
        return createdDateTime.format(DateTimeFormatter.ofPattern(Common.DATE_FORMAT));
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
