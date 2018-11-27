package codesquad.question;

import codesquad.user.User;

import javax.persistence.*;
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

    public Question() {
    }

    public Question(long index, User writer, String title, String contents, List<Answer> answers, boolean deleted) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.answers = answers;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void setIndex(Question question) {
        this.index = question.index;
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

    boolean isSameWriter(User user) {
        return this.writer.equals(user);
    }

    void deleteBy(User user) {
        if (!this.writer.equals(user)) {
            return;
        }

        if (!isOtherAnswerer(user)) {
            this.deleted = true;
            deleteAnswers();
        }
    }

    private boolean isOtherAnswerer(User user) {
        for (Answer answer : answers) {
            if (!answer.isSameWriter(user)) {
                return true;
            }
        }
        return false;
    }

    private void deleteAnswers() {
        for (Answer answer : answers) {
            answer.delete();
        }
    }
}
