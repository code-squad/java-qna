package codesquad.domain.qna;

import codesquad.domain.comment.Comment;
import codesquad.domain.user.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    @Column(name = "QUESTION_ID_FK")
    private Long id;

    @Column(length = User.USERID_LENGTH, nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String reportingDate;

    @OneToOne
    @JoinColumn(name = "USER_ID_FK")
    private User user;

    @Column
    private int countOfComment;

    public Question() {

    }

    public Question(Long id, String writer, String title, String contents, String reportingDate, User user, int countOfComment) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.reportingDate = reportingDate;
        this.user = user;
        this.countOfComment = countOfComment;
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

    public String getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(String reportingDate) {
        this.reportingDate = reportingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateQuestion(Question question) {
        this.contents = question.contents;
        this.reportingDate = question.reportingDate;
        this.title = question.title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCountOfComment() {
        return countOfComment;
    }

    public void setCountOfComment(int countOfComment) {
        this.countOfComment = countOfComment;
    }

    public void operateComment(int count) {
        this.countOfComment += count;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", reportingDate='" + reportingDate + '\'' +
                ", user=" + user +
                '}';
    }
}
