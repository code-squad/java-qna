package codesquad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 32)
    private String author;
    @Column(nullable = false, length = 64)
    private String title;
    private String content;
    private Timestamp date;

    public Question() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.date = Timestamp.valueOf(dateTime);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return user.userIdsMatch(author);
    }

    public void updateQuestion(Question updated) throws IllegalStateException { //TODO: 여기서 예외를 체크하는 게 좋은지 아니면 컨트롤러에서 할지??
        if (!this.id.equals(updated.id)) {
            throw new IllegalStateException("서로 다른 질문 아이디 값을 가지고 있습니다.");
        }
        if (!this.author.equals(updated.author)) {
            throw new IllegalStateException("질문자 아이디가 일치하지 않습니다.");
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
