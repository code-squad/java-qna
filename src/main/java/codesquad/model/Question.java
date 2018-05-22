package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long questionId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;

    @Column(nullable = false, length = 64)
    private String title;

    private String content;
    private Timestamp date;

    @OneToMany(mappedBy = "question")
    @OrderBy
    private List<Answer> answers;

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.date = Timestamp.valueOf(dateTime);
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

    public boolean authorAndUserIdMatch(User user) {
        return author.userIdsMatch(user);
    }

    public void updateQuestion(Question updated, User user) throws UnauthorizedRequestException {
        if (!authorAndUserIdMatch(user)) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.content = updated.content;
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
