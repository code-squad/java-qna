package codesquad.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
public class Question {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

    private LocalDateTime createDate;

    private int index;

    @OneToMany(mappedBy="question")
    @OrderBy("id desc")
    @JsonIgnore
    private List<Answer> answers;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss"));
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", index=" + index +
                '}';
    }


}
