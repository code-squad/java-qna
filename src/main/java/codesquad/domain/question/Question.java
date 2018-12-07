package codesquad.domain.question;

import codesquad.domain.AbstractEntity;
import codesquad.domain.answer.Answer;
import codesquad.domain.user.User;
import codesquad.exception.UserException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;


    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

    @Column(nullable = false)
    private boolean deleted = false;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void update(Question newQuestion) {
        newQuestion.matchWrite(this.writer);
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public void addAnswer(Answer answer) {
        this.countOfAnswer++;
        this.answers.add(answer);
    }

    public void deletedAnswer() {
        this.countOfAnswer--;

    }

    private boolean matchId(long id) {
        return getId() == id;
    }

    public void deleted() {
        if (answers.size() != countOfMatchUser()) {
            throw new UserException("다른사람이 쓴 댓글이 있습니다. 삭제가 불가능 합니다.");
        }

        for (Answer answer : answers) {
            answer.deleted();
        }
        deleted = true;
    }

    long countOfMatchUser() {
        return answers.stream()
                .filter(answer -> answer.matchUser(writer))
                .count();
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

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(Integer countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    public List<Answer> getAnswers() {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getCount() {
        return countOfAnswer;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", index='" + getId() + '\'' +
                '}';
    }

    public void matchWrite(User user) {
        if (!user.equals(this.writer)) {
            throw new UserException("작성자와 아이디가 다릅니다. 다시 로그인 해주세요");
        }
    }
}

