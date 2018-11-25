package codesquad.question;

import codesquad.exception.LoginUseIsNotMatchException;
import codesquad.user.User;

import javax.persistence.*;


@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;


    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    private String contents;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void update(Question newQuestion) {
        if (!newQuestion.matchId(this.id)) {
            throw new LoginUseIsNotMatchException();
        }
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    private boolean matchId(long id) {
        return this.id == id;
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
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", index='" + id + '\'' +
                '}';
    }

    public void matchWrite(User user) {
        if (!user.equals(this.writer)) {
            throw new LoginUseIsNotMatchException();
        }
    }
}

