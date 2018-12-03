package codesquad.answer;

import codesquad.question.Question;
import codesquad.user.User;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //db에 자동으로 새로 추가된 데이터의 pId 번호+1
    private long pId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Lob
    private String contents;

    private LocalDateTime date;
    private boolean deleted = false;

    public Answer() {
        this.date = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getAnswerDate() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return pId == answer.pId &&
                Objects.equals(writer, answer.writer) &&
                Objects.equals(question, answer.question) &&
                Objects.equals(contents, answer.contents) &&
                Objects.equals(date, answer.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId, writer, question, contents, date);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "pId=" + pId +
                ", writer=" + writer +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                '}';
    }

    public boolean matchUser(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void delete() {
        this.deleted = true;
    }
}
