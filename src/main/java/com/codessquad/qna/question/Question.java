package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonUtility;
import com.codessquad.qna.user.User;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    @Formula("(select count(*) from answer ans where ans.question_id = id)")
    private int replyCount;

    public Question() {}

    public Question(User writer, String title, String contents) {
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
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
        return createdDateTime.format(CommonUtility.DATE_TIME_FORMATTER);
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getFormattedUpdatedDateTime() {
        return updatedDateTime.format(CommonUtility.DATE_TIME_FORMATTER);
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void updateQuestionData(String title, String contents, LocalDateTime updatedDateTime) {
        this.title = title;
        this.contents = contents;
        this.updatedDateTime = updatedDateTime;
    }

    public boolean isWriterEqualsLoginUser(User loginUser) {
        if (loginUser == null) return false;
        return this.writer.equals(loginUser);
    }
}
