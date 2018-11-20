package codesquad.qna;

import codesquad.exception.UserIdNotMatchException;
import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User user;

    private String title;

    @Column(nullable = false, length = 1000)
    private String contents;

    public Question() {
    }

    public Question(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    // domain
    public void update(Question newQuestion, User updateUser) {
        if (!updateUser.matchId(this.user)) {
            throw new UserIdNotMatchException("USER_ID IS NOT CORRECT");
        }
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

}
