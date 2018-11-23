package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.user.User;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    private String contents;

    public Question() {

    }

    public Question(HttpSession session, String title, String contents) {
        this.writer = HttpSessionUtils.getUserFromSession(session);
        this.title = title;
        this.contents = contents;
    }

    void update(Question updatedQuestion) {
        if(updatedQuestion != null && this.writer.equals(updatedQuestion.writer)) {
            this.title = updatedQuestion.title;
            this.contents = updatedQuestion.contents;
        }
    }

    boolean isMatchWriter(User target) {
        return this.writer.equals(target);
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
