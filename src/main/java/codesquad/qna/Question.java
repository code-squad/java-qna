package codesquad.qna;

import javax.persistence.*;
import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String writer;

    @Column(length = 30)
    private String title;

    @Column(length = 10000)
    private String contents;
    private String curDate;

    public Question() {
        Date cur = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.curDate = sdf.format(cur);
    }

    public String getCurDate() {
        return curDate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(Question otherQuestion){
        this.title = otherQuestion.title;
        this.contents = otherQuestion.contents;
        this.curDate = otherQuestion.curDate;
    }

    public boolean matchWriter(Question otherQuestion){
        return otherQuestion.matchWriter(this.writer);
    }

    public boolean matchWriter(String otherWriter){
        return this.writer.equals(otherWriter);
    }
}
