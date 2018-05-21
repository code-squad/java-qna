package codesquad.model;

import codesquad.exceptions.UnauthorizedRequestException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_author"))
    private User author;

    @Column(nullable = false, length = 64)
    private String title;

    private String content;
    private Timestamp date;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
