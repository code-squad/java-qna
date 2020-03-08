package com.codessquad.qna.web.question;

import com.codessquad.qna.common.constants.FormatConstants;
import com.codessquad.qna.web.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @JsonIgnore
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

    public Long getId() {
        return id;
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
        return createdDateTime.format(FormatConstants.POST_DATA_DATE_TIME_FORMATTER);
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getFormattedUpdatedDateTime() {
        return updatedDateTime.format(FormatConstants.POST_DATA_DATE_TIME_FORMATTER);
    }

    public boolean isDeleted() {
        return isDeleted;
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

    public Long getAnswerCountExceptDeleted() {
        return answers.stream().filter(answer -> !answer.isDeleted()).count();
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

    public Question delete() {
        this.isDeleted = true;
        return this;
    }
}
