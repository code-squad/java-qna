package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User user;

    @Column(nullable = false, length = 64)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Embedded
    private Answers answers;

    private boolean deleted;
    private Integer countOfAnswers = 0;

    public boolean authorAndUserIdMatch(User user) {
        return this.user.equals(user);
    }

    public void updateQuestion(Question updated, User user) throws UnauthorizedRequestException {
        if (!authorAndUserIdMatch(user)) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.content = updated.content;

    }

    public void flagDeleted(User user) throws UnauthorizedRequestException {
        if (!this.authorAndUserIdMatch(user)) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.deleted = true;
        answers.flagDeleted(user);
        setDateLastModified(assignDateTime());
    }

    public void increaseAnswerCount() {
        this.countOfAnswers++;
    }

    public void decreaseAnswerCount() {
        this.countOfAnswers--;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        return answers.getAnswers();
    }

    public void setAnswers(List<Answer> answers) {
        this.answers.setAnswers(answers);
    }

    public Integer getCountOfAnswers() {
        return countOfAnswers;
    }

    public void setCountOfAnswers(Integer countOfAnswers) {
        this.countOfAnswers = countOfAnswers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + getId() +
                ", user='" + user + '\'' +
                ", title='" + title + '\'' +
                ", created=" + getDateCreated() +
                ", lastModified=" + getDateLastModified() +
                '}';
    }
}
