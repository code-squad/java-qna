package codesquad.domain.question;

import codesquad.domain.TimeEntity;
import codesquad.domain.user.User;
import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false, length = 12)
    private String writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void update(User sessionUser, Question updateQuestion) {
        if (!user.isMatch(sessionUser)) {
            return;
        }
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public boolean isMatch(User sessionUser) {
        return user.isMatch(sessionUser);
    }

    public boolean isMatch(Long id) {
        return this.id.equals(id);
    }
}
