package codesquad.qna.answers;

import codesquad.qna.questions.Question;
import codesquad.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Lob
    @Column(nullable = false)
    private String contents;

    @LastModifiedBy
    private LocalDateTime curDate;

    private boolean deleted;

    public Answer() {
        this.curDate = LocalDateTime.now();
        this.deleted = false;
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.curDate = LocalDateTime.now();
        this.deleted = false;
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

    public LocalDateTime getCurDate() {
        return curDate;
    }

    public String getFormattedCurDate() {
        return this.curDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setCurDate(LocalDateTime curDate) {
        this.curDate = curDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void updateContents(String contents, User user){
        if(!this.matchWriter(user)) throw new IllegalStateException("permission denied. 다른 사람의 글은 수정할 수 없습니다.");
        this.curDate = LocalDateTime.now();
        this.contents = contents;
    }

    public boolean matchWriter(User user){
        return this.writer.equals(user);
    }

    public void delete(User user) {
        if(!this.matchWriter(user)) throw new IllegalStateException("permission denied. 다른 사람의 글은 삭제할 수 없습니다.");
        this.deleted = true;
        this.curDate = LocalDateTime.now();
    }
}
