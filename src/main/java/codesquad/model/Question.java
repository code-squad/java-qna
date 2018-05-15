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
    private Long index;
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

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getIndex() {
        return index;
    }

    public boolean isMatch(String index) {
        return this.index.equals(Long.parseLong(index));
    }
}
