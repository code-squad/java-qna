package codesquad.answer;

import codesquad.domain.AbstractEntity;
import codesquad.qna.Question;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
    @JsonProperty
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @JsonProperty
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_user"))
    private User user;

    @JsonProperty
    @Column(nullable = false, length = 300)
    private String contents;

    @JsonProperty
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(Question question, User user, String contents) {
        this.question = question;
        this.user = user;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    // domain
    public boolean deletedState(User deleteUser) {
        if (!deleteUser.equals(user))
            return false;
        return this.deleted = true;
    }

}
