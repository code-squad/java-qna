package codesquad.question;

import codesquad.AbstractEntity;
import codesquad.answer.Answer;
import codesquad.exception.Result;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length=100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    private boolean deleted;

    @JsonIgnore
    @OneToMany(mappedBy="question", cascade = CascadeType.REMOVE)
    @OrderBy("id ASC")
    private List<Answer> answers;

    private int countOfAnswer;

    private Question() {}

    private Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.deleted = false;
        this.countOfAnswer = 0;
    }

    public static Question newInstance(User writer, String title, String contents) {
        return new Question(writer, title, contents);
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(int countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    void update(Question updatedQuestion) {
        this.setTitle(updatedQuestion.getTitle());
        this.setContents(updatedQuestion.getContents());
    }

    Result delete() {
        if (answers == null) this.deleted = true;
        for (Answer answer : answers) {
            if (!answer.isSameWriter(writer)) {
                if(answer.isDeleted()) continue;
                return Result.fail("Because the other user's answer exist, you can't edit the question.");
            }
        }
        for (Answer answer : answers) answer.delete();
        this.deleted = true;

        return Result.success();
    }

    public boolean isSameWriter(User sessionedUser) {
        return this.writer.equals(sessionedUser);
    }

    @JsonIgnore
    public List<Answer> getNotDeletedAnswers() {
        List<Answer> notDeletedAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            if(!answer.isDeleted()) notDeletedAnswers.add(answer);
        }

        return notDeletedAnswers;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deletedAnswer() {
        this.countOfAnswer -= 1;
    }

    @Override
    public String toString() {
        return "Question{" +
                super.toString() +
                "writer=" + writer.getUserId() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                ", answers=" + answers +
                ", countOfAnswer=" + countOfAnswer +
                '}';
    }
}
