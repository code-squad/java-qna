package codesquad.qna;

import codesquad.domain.AbstractEntity;
import codesquad.answer.Answer;
import codesquad.exception.UserIdNotMatchException;
import codesquad.user.User;
import codesquad.util.RegexParser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JsonProperty
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User user;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    @Column(nullable = false)
    private String contents;

    @JsonProperty
    private boolean deleted = false;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getNewLineContents() {
        return RegexParser.newLineMaker(contents);
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersLength() {
        return getAliveAnswers().size();
    }

    @JsonIgnore
    public List<Answer> getAliveAnswers() {
        return this.answers.stream().filter(a -> !a.isDeleted()).collect(Collectors.toList());
    }

    // domain
    public void update(Question newQuestion, User updateUser) {
        if (!updateUser.matchId(this.user)) {
            throw new UserIdNotMatchException("USER_ID IS NOT CORRECT");
        }
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public boolean deleteState(User deleteUser) {
        if (!user.equals(deleteUser)) return false;

        for (Answer answer : answers) {
            if (!answer.deletedState(deleteUser))
                return false;
        }

        return this.deleted = true;
    }
}
