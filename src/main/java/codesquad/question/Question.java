package codesquad.question;

import codesquad.user.User;
import codesquad.utils.TimeFormatter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User user;

    @CreationTimestamp
    private LocalDateTime time;

    Question() {
    }

    public Question(Long id, String title, String contents, User user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean matchUser(User sessionedUser) {
        return user.matchUser(sessionedUser);
    }

    public String getTime() {
        return TimeFormatter.commonFormat(time);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean update(Question updatedQuestion, User sessionedUser) {
        if(matchUser(sessionedUser)) {
            this.title = updatedQuestion.title;
            this.contents = updatedQuestion.contents;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", user='" + user + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

}
