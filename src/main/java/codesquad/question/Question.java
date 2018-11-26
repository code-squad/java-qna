package codesquad.question;

import codesquad.user.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Question {
    @Id
    @Column(name = "QUESTION_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    @Column(length = 100)
    private String title;
    @Lob
    private String contents;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers;
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(Question question) {
        this.index = question.index;
    }

    public void setIndex(long index) {
        this.index = index;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public boolean isSameWriter(User user) {
        return this.writer.equals(user);
    }

    public void changeAnswersDeleteState(boolean state) {
        for (Answer answer : answers) {
            answer.setDeleted(state);
        }
    }
}
