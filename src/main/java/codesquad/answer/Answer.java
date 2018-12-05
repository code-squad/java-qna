package codesquad.answer;

import codesquad.AbstractEntity;
import codesquad.question.Question;
import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Lob
    private String contents;

    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "pId=" + getPId() +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", date=" + getDate() +
                '}';
    }

    public boolean matchUser(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void delete(User loginUser) {
        if (this.writer.matchUser(loginUser)) {
            this.deleted = true;
        }
    }
}
