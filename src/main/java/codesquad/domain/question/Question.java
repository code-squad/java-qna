package codesquad.domain.question;

import codesquad.domain.TimeEntity;
import codesquad.domain.answer.Answers;
import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Question extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    @JsonIgnore
    private User user;

    @Embedded
    @JsonIgnore
    private Answers answers;

    @Column(nullable = false, length = 100)
    @JsonIgnore
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @JsonIgnore
    private String contents;

    @Column(nullable = false)
    @JsonIgnore
    private boolean deleted = false;

    @JsonProperty
    private Integer countOfAnswer = 0;

    @Builder
    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void update(Optional<User> maybeSessionUser, Question updateQuestion) {
        if (!user.equals(maybeSessionUser.get())) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public void delete(Optional<User> maybeSessionUser, Long pathId) {
        if (!isMatch(maybeSessionUser) || !id.equals(pathId)) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }

        if (deleted) {
            throw new ForbiddenRequestException("question.delete.state");
        }
        deleted = true;
        answers.delete(this);
    }

    public boolean isMatch(Optional<User> maybeOther) {
        if (!maybeOther.isPresent()) {
            throw new UnAuthorizedException("user.not.exist");
        }
        return user.equals(maybeOther.get());
    }

    public void addAnswer() {
        countOfAnswer++;
    }

    public void deleteAnswer() {
        countOfAnswer--;
    }
}
