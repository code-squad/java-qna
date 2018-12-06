package codesquad.answer;

import codesquad.AbstractEntity;
import codesquad.qna.Question;
import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isSameWriter(User otherUser) {
        return writer.equals(otherUser);
    }

    public void delete() {
        this.deleted = true;
    }

    @Override
    public String toString() {
        return "Answer{" +
                super.toString() +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createDate=" + getFormattedCreateDate() +
                '}';
    }
}
