package codesquad.domain.qna;

import codesquad.domain.qna.comment.Comment;
import codesquad.domain.user.User;
import codesquad.domain.util.TimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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

    @Lob /* 피드백5) 본문내용 글자수를 위해 Lob 어노테이션 설정! */
    @Column(nullable = false)
    private String contents;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @OneToOne
    @JoinColumn(name = "USER_ID_FK")
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("createdDateTime desc")
    private List<Comment> comments = new ArrayList<>();

    @Column
    private Boolean deleted = false;

    public Question() {

    }

    public Question(Long id, String writer, String title, String contents, User user, List<Comment> comments, int countOfComment) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.comments = comments;
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
        return TimeFormat.getTimeFormat(createdDateTime);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateQuestion(Question question) {
        this.contents = question.contents;
        this.title = question.title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getCommentsCount() {
        return this.comments.size();
    }
    /* 피드백4) 본인에 글인지 확인을 위한 메소드 */
    public boolean identification(User user) {
        return this.user.equals(user);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        deleted = deleted;
    }

    public void deletionProcess() {
        this.deleted = true;
    }

    public void identificationComment(HttpSession httpSession) {
        for(Comment comment : comments) {
            comment.identification(httpSession);
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
