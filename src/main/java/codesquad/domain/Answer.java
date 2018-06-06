package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity

public class Answer extends AbstractEntiry {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_id"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonProperty
    private Question question;

    @Column(nullable = false)
    @JsonProperty
    private String comment;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User writer, Question question, String comment) {
        this.writer = writer;
        this.question = question;
        this.comment = comment;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean update(Answer updateAnswer, User updateUser) {
        if (updateAnswer == null) {
            throw new NullPointerException("answer.null");
        }
        if (!isMatchedUser(updateUser)) {
            return false;
        }

        this.comment = updateAnswer.comment;
        return true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }

    public boolean isMatchedUser(User otherUser) {
        return writer.isMatchedUser(otherUser);
    }
}
