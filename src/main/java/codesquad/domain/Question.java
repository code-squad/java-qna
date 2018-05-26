package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Embedded
    @JsonIgnore
    private Answers answers;

    private String title;
    @Lob
    private String contents;

    public Result update(Question question, User loginedUser) {
        if (!loginedUser.isSameWriter(question)) {
            return Result.fail("You can't update another user's Question");
        }
        this.title = question.title;
        this.contents = question.contents;
        return Result.ok();
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers.getAnswers();
    }

    public void setAnswers(List<Answer> answers) {
        this.answers.setAnswers(answers);
    }

    public int getNumberOfAnswer() {
        return answers.getSizeOfAnswers();
    }

    @Override
    public String toString() {
        return "Question{" +
               super.toString() +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
