package codesquad.question;

import codesquad.AbstractEntity;
import codesquad.answer.Answer;
import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    @OrderBy("pId ASC")
    @JsonIgnore
    private List<Answer> answers;

    private String title;
    @Lob
    private String contents;

    private int answersSize = 0;
    private boolean deleted = false;

    public Question() {

    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getAnswersSize() {
        return answersSize;
    }

    public void plusAnswersSize() {
        this.answersSize++;
    }

    public void minusAnswersSize() {
        this.answersSize--;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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

    void update(Question updateQuestion, User loginUser) {
        if (!HttpSessionUtils.isValid(loginUser, this)) throw new IllegalArgumentException();
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public boolean matchUser(String userId) {
        return this.writer.equals(userId);
    }

    public boolean matchUser(User loginUser) {
        return this.writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Question{" +
                "pId=" + getPId() +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", answersSize=" + answersSize +
                ", date=" + getDate() +
                ", deleted=" + deleted +
                '}';
    }

    private boolean isNotExistOtherUser() {
        for (Answer answer : answers) {
            if (!answer.matchUser(this.writer)) return false;
        }
        return true;
    }

    public void delete(User loginUser) {
        if (isNotExistOtherUser()) {
            this.deleted = true;
            for (Answer answer : answers) {
                answer.delete(loginUser);
            }
        }
    }
}