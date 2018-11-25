package codesquad.answer;

import codesquad.exception.AnswerException;
import codesquad.question.Question;
import codesquad.exception.LoginUseIsNotMatchException;
import codesquad.user.User;

import javax.persistence.*;

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

    public Answer() {
    }

    public Answer(Question question, User user, String contents) {
        this.question = question;
        this.user = user;
        this.contents = contents;
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

    public void matchSessionUser(User sessionUser) {
        if (!sessionUser.equals(this.user)) {
            throw new LoginUseIsNotMatchException();
        }
    }

    public void update(Answer updatedAnswer) {
        if (!updatedAnswer.matchId(this.id)) {
            throw new AnswerException("아이디가 다름");
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
}
