package codesquad.domain.model;

import codesquad.domain.utils.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    private String contents;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    @OrderBy("id DESC")
    @JsonIgnore
    private List<Answer> answers;

    @JsonProperty
    private Integer countOfAnswer = 0;

    @Builder
    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public boolean matchWriter(User user) {
        return this.writer.equals(user);
    }

    public void addAnswer() {
        this.countOfAnswer++;
    }

    public void deleteAnswer() {
        this.countOfAnswer--;
    }
}
