package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

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

    @JsonProperty
    private Timestamp date;

    public Answer() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.date = Timestamp.valueOf(dateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void validateUser(User user) {
        if (this.user.userIdsMatch(user)) {
            return;
        }
        throw new UnauthorizedRequestException("Question.userId.mismatch");
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}