package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;
    // Lob이라는 어노테이션을 추가하면 255이상으로 작성이 가능하다.

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    private List<Answer> answers;

    @JsonProperty
    private Integer countOfAnswer = 0;

    public Question() {}

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;

    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getContents() {
        return contents;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Question update(String title, String contents, User loginUser) {
        if (!isSameWriter(loginUser))
            throw new IllegalStateException("글쓴이만 수정할 수 있습니다.");
        this.title = title;
        this.contents = contents;
        return this;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }


}
