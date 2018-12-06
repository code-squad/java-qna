package codesquad.answer;

import codesquad.question.Question;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey, name = "fk_answer_question")
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey, name = "fk_answer_writer")
    private User writer;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    public Answer() {
    }

    public Answer(User user, Question question, String contents) {
        this.writer = user;
        this.question = question;
        this.contents = contents;
        createDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss"));
    }

    public boolean isSameWriter(User sessionedUser) {
        return writer.equals(sessionedUser);
    }
}
