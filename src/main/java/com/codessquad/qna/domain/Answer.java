package com.codessquad.qna.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    private String contents;

    private boolean deleted = false;

    public Question getQuestion() {
        return question;
    }

    public boolean getDeleted() {
        return this.deleted;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void update(Answer answer) {
        if (!isValid(answer)) {
            throw new RuntimeException("InvalidUpdateAnswer");
        }
        this.contents = answer.contents;

    }

    private boolean isValid(Answer answer) {
        return answer.contents != null;
    }

    public void delete() {
        this.deleted = true;
    }

    @Override
    public String toString() {
        return "Answer{" +
                super.toString() +
                ", question=" + question +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                '}';
    }
}
