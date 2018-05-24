package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long questionId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;

    @Column(nullable = false, length = 64)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;

    @Embedded
    private Answers answers;

    private boolean deleted;
    private Integer countOfAnswers = 0;

    public Question() {
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        this.date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(dateTime);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
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

    public String getDate() {
        return date.toString();
    }

    public void setQuestionId(Long id) {
        this.questionId = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Answer> getAnswers() {
        return answers.getAnswers();
    }

    public void setAnswers(List<Answer> answers) {
        this.answers.setAnswers(answers);
    }

    public boolean authorAndUserIdMatch(User user) {
        return author.userIdsMatch(user);
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
    }

    public void increaseAnswerCount() {
        this.countOfAnswers++;
    }

    public void decreaseAnswerCount() {
        this.countOfAnswers--;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + questionId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
