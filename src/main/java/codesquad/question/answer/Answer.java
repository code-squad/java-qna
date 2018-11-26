package codesquad.question.answer;

import codesquad.question.Question;
import codesquad.user.User;
import codesquad.utils.TimeFormatter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User user;

    @CreationTimestamp
    private LocalDateTime createDate;

    @Lob
    private String comment;

    Answer() {
    }

    public Answer(Question question, User user, String comment) {
        this.question = question;
        this.user = user;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isSameUser(User user) {
        return this.user.equals(user);
    }

    public String getCreateDate() {
        return TimeFormatter.commonFormat(this.createDate);
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", user=" + user +
                ", createDate='" + createDate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
