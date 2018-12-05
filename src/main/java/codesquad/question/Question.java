package codesquad.question;

import codesquad.AbstractEntity;
import codesquad.answer.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User writer;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Answer> answers;

    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    private String contents;

    private Integer countOfAnswer = 0;

    private boolean deleted;

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.deleted = false;
    }

    boolean isMatchWriter(User target) {
        return this.writer.equals(target);
    }

    void update(User loggedInUser, Question updatedQuestion) {
        if(!loggedInUser.equals(this.writer)) {
            throw new IllegalStateException("작성자만 수정 가능합니다.");
        }

        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }

    void delete(User loggedInUser) {
        if(!isMatchWriter(loggedInUser)) {
            throw new IllegalStateException("작성자만 삭제 가능합니다.");
        }

        if(this.answers == null) {
            this.deleted = true;
        }

        if(isAuthorizedAnswers(this.answers)) {
            for (Answer answer : this.answers) {
                answer.delete(loggedInUser);
            }
            this.deleted = true;
        }

        //TODO: else를 쓰지않고 삭제가 불가능한 케이스 알려주기, 다른 작성자의 답변이 남아있을 경우
    }

    private boolean isAuthorizedAnswers(List<Answer> answers) {
        if(answers == null) {
            return false;
        }

        for (Answer answer : answers) {
            if(!answer.isMatchWriter(this.writer)){
                return false;
            }
        }

        return true;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(Integer countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + getId() +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
