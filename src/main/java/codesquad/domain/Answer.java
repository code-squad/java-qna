package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;
    // Answer가 question에 종속되어있다는 것을 의미한다.

    @Lob
    @JsonProperty
    private String contents;

    public Answer() {}

    public Answer(User writer, Question question , String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }

    @Override
    public String toString() {
        return "Answer{" + super.toString() +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                '}';
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }


}
