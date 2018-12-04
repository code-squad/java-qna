package codesquad.qna.answers;

import codesquad.AbstractEntity;
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
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Lob
    @Column(nullable = false)
    private String contents;

    private boolean deleted;

    public Answer() {
        this.deleted = false;
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.deleted = false;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void updateContents(String contents, User user){
        if(!this.matchWriter(user)) throw new IllegalStateException("permission denied. 다른 사람의 글은 수정할 수 없습니다.");
        this.contents = contents;
    }

    public boolean matchWriter(User user){
        return this.writer.equals(user);
    }

    public void delete(User user) {
        if(!this.matchWriter(user)) throw new IllegalStateException("permission denied. 다른 사람의 글은 삭제할 수 없습니다.");
        this.deleted = true;
    }
}
