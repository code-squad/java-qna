package codesquad.question;

import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pId;

    @Column(nullable = false, length = 20)
    private String writer;

//    @ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
//    private User writer;
    
    private String title;
    @Lob
    private String contents;
    private String date;

    public Question() {
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

    void update(Question updateQuestion, HttpSession session) {
        if (!HttpSessionUtils.isValid(session, this)) throw new IllegalArgumentException();
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public boolean matchUserId(String userId) {
        return this.writer.equals(userId);
    }

    public boolean matchUserId(User loginuser) {
        return loginuser.matchWriter(this.writer);
    }
}
