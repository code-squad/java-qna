package codesquad.qna.questions;

import codesquad.qna.answers.Answer;
import codesquad.user.User;
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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(length = 30)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @LastModifiedBy
    private LocalDateTime curDate;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question() {
        this.curDate = LocalDateTime.now();
        this.answers = new ArrayList<>();
    }

    public String getCurDate() {
        return this.curDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setCurDate(LocalDateTime curDate) {
        this.curDate = curDate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersCount(){
        return answers.size();
    }

    public void update(Question otherQuestion){
        this.title = otherQuestion.title;
        this.contents = otherQuestion.contents;
        this.curDate = otherQuestion.curDate;
    }

    public boolean matchWriter(User user){
        return this.writer.equals(user);
    }
}
