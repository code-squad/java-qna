package codesquad.domain.answer;

import codesquad.domain.TimeEntity;
import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Answer extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_user"))
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Answer(User user, Question question, String contents) {
        this.user = user;
        this.question = question;
        this.contents = contents;
    }

    public void delete(Question requestQuestion) {
        validateDelete();
        if (!requestQuestion.isMatch(Optional.of(user))) {
            throw new UnAuthorizedException("answer.user.mismatch.request.user");
        }
        deleted = true;
    }

    public void delete(Optional<User> maybeSessionUser) {
        validateDelete();
        maybeSessionUser.filter(sessionUser -> user.equals(sessionUser)).orElseThrow(() -> new UnAuthorizedException("answer.user.mismatch.request.user"));
        deleted = true;
    }

    private void validateDelete() {
        if (deleted) {
            throw new ForbiddenRequestException("answer.not.exist");
        }
    }
}
