package codesquad.answer;

import codesquad.AbstractEntity;
import codesquad.question.Question;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    @Lob
    private String contents;

    private boolean deleted;

    public Answer() {

    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.deleted = false;
    }

    public void update(User loggedInUser, Answer target) {
        if(!loggedInUser.equals(this.writer)) {
            throw new IllegalStateException("작성자만 수정 가능합니다.");
        }

        this.contents = target.contents;
    }

    public void delete(User loggedInUser) {
        if(!isMatchWriter(loggedInUser)) {
            throw new IllegalStateException("작성자만 삭제 가능합니다.");
        }

        this.deleted = true;
    }

    public boolean isMatchWriter(User target) {
        return this.writer.equals(target);
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

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", question=" + question +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
