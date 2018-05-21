package codesquad.domain.answer;

import codesquad.domain.TimeEntity;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import lombok.*;

import javax.persistence.*;

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

    @Builder
    public Answer(User user, Question question, String contents) {
        this.user = user;
        this.question = question;
        this.contents = contents;
    }
}
