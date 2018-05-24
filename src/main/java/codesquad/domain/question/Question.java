package codesquad.domain.question;

import codesquad.domain.TimeEntity;
import codesquad.domain.answer.Answer;
import codesquad.domain.answer.Answers;
import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
    private User user;

    @Embedded
    private Answers answers;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

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
}
