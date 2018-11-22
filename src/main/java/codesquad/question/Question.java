package codesquad.question;

import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    private String contents;

    //TODO: 자신의 글인치 체크 후에 실행되도록
    public void update(Question updatedQuestion) {
            this.title = updatedQuestion.title;
            this.contents = updatedQuestion.contents;
    }

    public boolean isMatchWriter(User target) {
        return this.writer.equals(target);
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
