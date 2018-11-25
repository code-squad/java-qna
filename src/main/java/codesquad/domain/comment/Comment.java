package codesquad.domain.comment;

import codesquad.domain.qna.Question;
import codesquad.domain.user.User;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 자동으로 숫자를 증가해서 관리 //
    private Long commentId;

    @OneToOne
    @JoinColumn(name = "USER_ID_FK")
    private User user;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String reportingDate;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID_FK")
    private Question question;

    public Comment() {

    }

    public Comment(Long commentId, User user, String contents, String reportingDate, Question question) {
        this.commentId = commentId;
        this.user = user;
        this.contents = contents;
        this.reportingDate = reportingDate;
        this.question = question;
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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void updateComment(Comment comment) {
        this.contents = comment.contents;
        this.reportingDate = comment.reportingDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", user=" + user +
                ", contents='" + contents + '\'' +
                ", reportingDate='" + reportingDate + '\'' +
                ", question=" + question +
                '}';
    }
}
