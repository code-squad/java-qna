package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Column(nullable = false)
    @JsonProperty
    private String date;

    private boolean deleted;

    public Answer() {
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        this.date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(dateTime);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Result flagDeleted(AnswerRepository repository, User user) {
        if (!(this.user.userIdsMatch(user) || this.question.authorAndUserIdMatch(user))) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.deleted = true;
        repository.save(this);
        return Result.ofSuccess();
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