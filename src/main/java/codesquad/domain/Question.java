package codesquad.domain;

import codesquad.web.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;
    private String contents;
    private LocalDateTime createDate;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public Question() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean matchUser(User user) {
        return writer.equals(user);
    }

    public void update(Question updatedQuestion, HttpSession session) {
        checkEqualSession(session);

        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public void checkEqualSession(HttpSession session) {
        User userFromSession = HttpSessionUtils.getUserFromSession(session);
        if (!matchUser(userFromSession)) {
            throw new IllegalStateException("Don't manipulate Other's contents");
        }
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }

        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
