package codesquad.domain.question;

import codesquad.domain.TimeEntity;
import codesquad.domain.answer.Answer;
import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.user.User;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    /*@Type(type = "org.hibernate.type.NumericBooleanType")*/
    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void update(User sessionUser, Question updateQuestion) {
        if (!user.equals(sessionUser)) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public void delete(User sessionUser, Long pathId) {
        if (hasAnswer()) {
            throw new UnAuthorizedException("question.have.answer");
        }

        if (!isMatch(sessionUser) || !id.equals(pathId)) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }

        if (deleted) {
            throw new ForbiddenRequestException("question.delete.state");
        }
        deleted = true;
    }

    public boolean isMatch(User sessionUser) {
        return user.equals(sessionUser);
    }

    private boolean hasAnswer() {
        return answers.size() != 0;
    }
}
