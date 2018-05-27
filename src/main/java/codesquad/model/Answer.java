package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonIgnoreProperties("answers")
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    @JsonProperty
    private User user;

    @Column(nullable = false, length = 62)
    @JsonProperty
    private String content;

    private boolean deleted;

    public Result flagDeleted(User user) throws UnauthorizedRequestException {
        if (!(this.user.equals(user) || this.question.authorAndUserIdMatch(user))) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.deleted = true;
        question.decreaseAnswerCount();
        return Result.ofSuccess();
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        question.increaseAnswerCount();
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", question=" + question +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", created=" + getDateCreated() +
                ", lastModified=" + getDateLastModified() +
                '}';
    }
}