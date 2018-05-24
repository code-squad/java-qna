package codesquad.domain;

import codesquad.web.HttpSessionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @JsonProperty
    private Integer countOfAnswer = 0;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Question() {

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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(Integer countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    public boolean matchUser(User user) {
        return writer.equals(user);
    }

    public void update(Question updatedQuestion, HttpSession session) {
        if (!checkEqualSession(session)) {
            throw new IllegalStateException("update error");
        }

        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public boolean checkEqualSession(HttpSession session) {
        User userFromSession = HttpSessionUtils.getUserFromSession(session);
        if (!matchUser(userFromSession)) {
            return false;
        }

        return true;
    }

    public void addAnswer() {
        this.countOfAnswer++;
    }

    public void deleteAnswer() {
        this.countOfAnswer--;
    }

    @Override
    public String toString() {
        return "Question{" + super.toString() +
                "writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", countOfAnswer=" + countOfAnswer +
                '}';
    }
}
