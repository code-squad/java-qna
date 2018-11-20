package codesquad.qna;

import codesquad.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @Column(nullable = false)
    private String title;

    @Lob
    private String contents;

    private LocalDateTime timer;

    public Answer(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
    }
}
