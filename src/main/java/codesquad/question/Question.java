package codesquad.question;

import codesquad.answer.Answer;
import codesquad.exception.UserException;
import codesquad.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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

    private LocalDateTime date;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void update(Question newQuestion) {
        if (!newQuestion.matchId(this.id)) {
            throw new UserException("작성자와 아이디가 다릅니다. 다시 로그인 해주세요");
        }
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public String getFormattedDate() {
        if (this.date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public LocalDateTime getDate() {
        return date;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getConut() {
        return String.valueOf(answers.size());
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
            throw new UserException("작성자와 아이디가 다릅니다. 다시 로그인 해주세요");
        }
    }
}

