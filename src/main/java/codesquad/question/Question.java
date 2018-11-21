package codesquad.question;

import codesquad.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    private String title;
    @Lob
    private String contents;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_answer"))
    private Collection<Answer> answers;

    public long getIndex() {
        return index;
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

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
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

    public void setIndex(Question question) {
        this.index = question.index;
    }
}
