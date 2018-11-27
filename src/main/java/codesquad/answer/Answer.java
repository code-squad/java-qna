package codesquad.answer;

import codesquad.exception.UserIdNotMatchException;
import codesquad.qna.Question;
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
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_user"))
    private User user;

    @Column(nullable = false, length = 300)
    private String contents;

    private LocalDateTime createDate;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(Question question, User user, String contents) {
        this.question = question;
        this.user = user;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getFormattedCreateDate() {
        if (createDate == null) return "";
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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
