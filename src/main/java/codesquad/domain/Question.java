package codesquad.domain;

import codesquad.web.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

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
        if (!checkEqualSession(session)) {
            throw new IllegalStateException("update error");
        }

        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public boolean checkEqualSession(HttpSession session) {
        User userFromSession = HttpSessionUtils.getUserFromSession(session);
        if (!matchUser(userFromSession)) {
            return false;
        }

        return true;
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }

        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
