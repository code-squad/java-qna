package codesquad.domain.answer;

import codesquad.domain.AbstractEntity;
import codesquad.exception.UserException;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User user;

    @Lob
    @JsonProperty
    private String contents;

    @Column(nullable = false)
    @JsonProperty
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(Question question, User user, String contents) {
        this.question = question;
        this.user = user;
        this.contents = contents;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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


    public boolean matchUser(User user) {
        return user.equals(this.user);
    }

    public void update(Answer updatedAnswer) {
        if (!updatedAnswer.matchUser(this.user)) {
            throw new UserException("아이디가 다름");
        }
        this.contents = updatedAnswer.contents;
    }

    private boolean matchId(long id) {
        return getId() == id;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", question=" + question +
                ", user=" + user +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void deleted() {
        question.deletedAnswer();
        deleted = true;
    }
}
