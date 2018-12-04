package codesquad.question;

import codesquad.answer.Answer;
import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    @OrderBy("pId ASC")
    @JsonIgnore
    private List<Answer> answers;

    private String title;
    @Lob
    private String contents;

    private int answersSize = 0;
    private LocalDateTime date;
    private boolean deleted = false;

    public Question() {
        this.date = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getAnswersSize() {
        return answersSize;
    }

    public void plusAnswersSize() {
        this.answersSize++;
    }

    public void minusAnswersSize() {
        this.answersSize--;
    }

    public String getDate() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public long getPId() {
        return pId;
    }

    public void setPId(long pId) {
        this.pId = pId;
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

    void update(Question updateQuestion, User loginUser) {
        if (!HttpSessionUtils.isValid(loginUser, this)) throw new IllegalArgumentException();
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public boolean matchUser(String userId) {
        return this.writer.equals(userId);
    }

    public boolean matchUser(User loginUser) {
        return this.writer.equals(loginUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return pId == question.pId &&
                Objects.equals(writer, question.writer) &&
                Objects.equals(title, question.title) &&
                Objects.equals(contents, question.contents) &&
                Objects.equals(date, question.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId, writer, title, contents, date);
    }

    @Override
    public String toString() {
        return "Question{" +
                "pId=" + pId +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                '}';
    }

    private boolean isNotExistOtherUser() {
        for (Answer answer : answers) {
            if (!answer.matchUser(this.writer)) return false;
        }
        return true;
    }

    public void delete() {
        if (isNotExistOtherUser()) {
            this.deleted = true;
            for (Answer answer : answers) {
                answer.delete();
            }
        }
    }
}