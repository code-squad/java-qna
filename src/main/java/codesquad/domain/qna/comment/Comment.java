package codesquad.domain.qna.comment;

import codesquad.domain.qna.Question;
import codesquad.domain.user.User;
import codesquad.domain.util.Session;
import codesquad.domain.util.TimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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

    @Column(nullable = true)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @OneToOne
    @JoinColumn(name = "QUESTION_ID_FK")
    private Question question;

    @Transient
    private boolean identification;

    @Column(nullable = true)
    private boolean deleted = false;

    public Comment() {

    }

    public Comment(Long commentId, User user, String contents, Question question) {
        this.commentId = commentId;
        this.user = user;
        this.contents = contents;
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getReportingDate() {
        return TimeFormat.getTimeFormat(createdDateTime);
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
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void identification(HttpSession httpSession) {
        User sessionUser = Session.obtainUser(httpSession);
        if(Session.obtainUser(httpSession) != null) {
            identification = sessionUser.equals(user);
        }
    }

    public boolean getIdentification() {
        return identification;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                //", user=" + user +
                ", contents='" + contents + '\'' +
                ", createdDateTime=" + createdDateTime +
                //", question=" + question +
                ", identification=" + identification +
                ", deleted=" + deleted +
                '}';
    }
}
