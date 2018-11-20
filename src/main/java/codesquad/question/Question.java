package codesquad.question;

import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @Column(nullable = false, length = 20)
    private String writer;
    @Column(nullable = false, length = 40)
    private String title;
    @Column(nullable = false, length = 10000)
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User user;
    private String time;

    Question() {
    }

    public Question(Long index, String writer, String title, String contents, User user, String time) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.time = time;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean matchUser(User sessionedUser) {
        return user.equals(sessionedUser);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "index=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", user='" + user + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

}
