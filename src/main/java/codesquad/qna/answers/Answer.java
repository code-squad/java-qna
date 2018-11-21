package codesquad.qna.answers;

import codesquad.qna.questions.Question;
import codesquad.user.User;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private Question question;

    @Column(length = 10000)
    private String contents;

    @Column(length = 20)
    private String curDate;

    public Answer() {
        this.setCurDate();
    }

    private void setCurDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.curDate = sdf.format(date);
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

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public void updateContents(String contents){
        this.contents = contents;
        this.setCurDate();
    }

    public boolean matchWriter(User user){
        return this.writer.equals(user);
    }
}
