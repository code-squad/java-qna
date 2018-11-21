package codesquad.question;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pId;

    @Column(nullable = false)
    private String writer;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String contents;
    private String date;

    public Question(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.date = simpleDateFormat.format(now);
    }

    public String getDate() {
        return date;
    }

    public long getPId() {
        return pId;
    }

    public void setPId(long pId) {
        this.pId = pId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
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

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

}
