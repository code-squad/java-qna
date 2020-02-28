package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonConstants;
import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

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

    @Column(nullable = false)
    private LocalDateTime updatedDateTime;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "question")
    private Collection<Answer> answers;

    public Question() {}

    public Question(User writer, String title, String contents) {
        LocalDateTime now = LocalDateTime.now();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = now;
        this.updatedDateTime = now;
        this.isDeleted = false;
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
        return createdDateTime.format(CommonConstants.POST_DATA_DATE_TIME_FORMATTER);
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getFormattedUpdatedDateTime() {
        return updatedDateTime.format(CommonConstants.POST_DATA_DATE_TIME_FORMATTER);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswerCount() {
        return answers.size();
    }

    public void updateQuestionData(String title, String contents, LocalDateTime updatedDateTime) {
        this.title = title;
        this.contents = contents;
        this.updatedDateTime = updatedDateTime;
    }

    public boolean isWrittenBy(User loginUser) {
        if (loginUser == null) {
            return false;
        }
        return this.writer.equals(loginUser);
    }

    public boolean isDeletable() {
        // 게시물 작성자와 답변 작성자가 일치
        boolean isSameWriter = answers.stream().allMatch(a -> this.writer.equals(a.getWriter()));
        return answers.isEmpty() || isSameWriter;
    }

}
