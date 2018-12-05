package codesquad.answer;

import codesquad.AbstractEntity;
import codesquad.qna.Question;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @Lob
    @JsonProperty
    private String contents;

    private LocalDateTime createDate;

    private boolean deleted = false;

    public Answer() {}

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.question = question;
        this.createDate = LocalDateTime.now();
    }

//    public Long getId() {
//        return this.getId();
//    }

    public Question getQuestion() {

        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFormattedCreateDate() {
        List<String> adsf = new ArrayList<>();
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void changeDeletedTrue() {
        deleted = true;
    }

    public boolean isSameWriter(User loginUser) {
        return writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Answer{" +
                super.toString() +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                '}';
    }
}
