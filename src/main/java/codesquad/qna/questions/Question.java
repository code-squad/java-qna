package codesquad.qna.questions;

import codesquad.AbstractEntity;
import codesquad.qna.answers.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(length = 30)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    private boolean deleted;

    public Question() {
        this.answers = new ArrayList<>();
        this.deleted = false;
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

    public int getAnswersCount(){
        int count = 0;
        for (Answer answer : answers) {
            if(!answer.isDeleted()) count++;
        }
        return count;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void update(Question otherQuestion){
        if(!otherQuestion.matchWriter(this.writer)) throw new IllegalStateException("permission denied. 다른 사람의 글은 수정할 수 없습니다.");
        this.title = otherQuestion.title;
        this.contents = otherQuestion.contents;
    }

    public boolean matchWriter(User user){
        return this.writer.equals(user);
    }

    public void delete(User user) {
        if(!this.matchWriter(user)) throw new IllegalStateException("permission denied. 다른 사람의 글은 삭제할 수 없습니다.");
        this.deleteAnswers(user);
        this.deleted = true;
    }

    private void deleteAnswers(User user){
        if(!isAnswersPermission(user)) throw new IllegalStateException("permission denied. 다른 사람의 답변이 존재하여 삭제할 수 없습니다.");
        for (Answer answer : answers) {
            answer.delete(user);
        }
    }

    private boolean isAnswersPermission(User user){
        for (Answer answer : answers) {
            if(!answer.matchWriter(user)) return false;
        }
        return true;
    }
}
