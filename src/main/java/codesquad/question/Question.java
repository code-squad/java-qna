package codesquad.question;

import codesquad.answer.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    private LocalDateTime createDate;

    private String contents;

    private Integer answerCount = 0;

    public Question() {
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }


    public List<Answer> getAnswers() {
        return answers;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public User getWriter() {
        return writer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean matchUserId(String userId) {
        return this.writer.getUserId().equals(userId);
    }

    public void update(Question newQuestion) {
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public String getDate() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void increaseAnswer() {
        this.answerCount += 1;
    }

    public void decreaseAnswer() {
        this.answerCount -= 1;
    }
}