package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.springframework.core.annotation.Order;

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

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;

    @Column(nullable = false, length = 64)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    private List<Answer> answers;

    private boolean deleted;

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
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean authorAndUserIdMatch(User user) {
        return author.userIdsMatch(user);
    }

    public void updateQuestion(QuestionRepository repository, Question updated, User user) throws UnauthorizedRequestException {
        if (!authorAndUserIdMatch(user)) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.content = updated.content;
        repository.save(this);
    }

    public void flagDeleted(QuestionRepository questionRepo, AnswerRepository answerRepo, User user) throws UnauthorizedRequestException {
        if (!this.authorAndUserIdMatch(user)) {
            throw new UnauthorizedRequestException("Question.userId.mismatch");
        }
        this.deleted = true;
        questionRepo.save(this);
        for (Answer answer : answers) {
            answer.flagDeleted(answerRepo, user);
        }
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
