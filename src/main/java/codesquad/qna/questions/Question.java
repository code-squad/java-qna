package codesquad.qna.questions;

import codesquad.qna.answers.Answer;
import codesquad.user.User;

import javax.persistence.*;
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
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Column(length = 30)
    private String title;

    @Column(length = 10000)
    private String contents;

    @Column(length = 20)
    private String curDate;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question() {
        Date cur = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.curDate = sdf.format(cur);
        this.answers = new ArrayList<>();
    }

    public String getCurDate() {
        return curDate;
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
