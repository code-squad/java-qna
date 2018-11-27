package codesquad.answer;

import codesquad.exception.AnswerException;
import codesquad.exception.UserException;
import codesquad.question.Question;
import codesquad.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Lob
    private String contents;

    private LocalDateTime date;

    @Column(nullable = false)
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(Question question, User user, String contents) {
        this.question = question;
        this.user = user;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public String getFormattedDate() {
        if (this.date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
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
        return this.id == id;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", user=" + user +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void deleted() {
        deleted = true;
    }
}
