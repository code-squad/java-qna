package codesquad.domain.question;

import codesquad.domain.TimeEntity;
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
}
