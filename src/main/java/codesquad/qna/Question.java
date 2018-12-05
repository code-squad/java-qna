package codesquad.qna;

import codesquad.AbstractEntity;
import codesquad.answer.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 100)
    private String title;
    @Lob
    private String contents;
    private boolean deleted;

    @JsonProperty
    private int countOfAnswer = 0;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    public Question() {
    }

    public Question (User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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
        return  answers.stream().filter(x -> !x.isDeleted()).collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean update(Question updateQuestion, User loginUser) {
        if(isSameWriter(loginUser)) {
            this.title = updateQuestion.title;
            this.contents = updateQuestion.contents;
            return true;
        }
        return false;
    }

    public String getAnswersSize() {
        System.out.println("### size " + answers.stream().filter(x -> !x.isDeleted()).count());
        return String.valueOf(answers.stream().filter(x -> !x.isDeleted()).count());
    }

    public boolean isEmptyAnswers() {
        return answers.isEmpty();
    }

    public boolean delete() {
        // 비었으면
        if (answers.isEmpty()) {
            deleted = true;
            return true;
        }

        for (Answer answer : answers) {
            if (!answer.isSameWriter(writer)) {
                return false;
            }
        }
        answers.forEach(Answer::delete);
        deleted = true;
        return true;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    @Override
    public String toString() {
        return "Question{" +
                super.toString() +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
