package codesquad.qna;

import codesquad.answer.Answer;
import codesquad.exception.UserIdNotMatchException;
import codesquad.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User user;

    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "question" ,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersLength() {
        return answers.size();
    }
}
