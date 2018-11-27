package codesquad.question;

import codesquad.LocalDateTimeConverter;
import codesquad.user.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length=100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    private Question() {}

    private Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }

    public static Question newInstance(User writer, String title, String contents) {
        return new Question(writer, title, contents);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getFormattedCreatedDate() {
        if (createdDate == null) return "";
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    void update(Question updatedQuestion) {
        this.setTitle(updatedQuestion.getTitle());
        this.setContents(updatedQuestion.getContents());
    }

    public boolean isSameWriter(User sessionedUser) {
        return this.writer.equals(sessionedUser);
    }
}
