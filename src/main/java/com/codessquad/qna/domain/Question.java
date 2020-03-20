package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    @NotBlank
    @Column(length = 50)
    @JsonProperty
    private String title;

    @NotBlank
    @Type(type = "text")
    @JsonProperty
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

    private boolean deleted;

    @Formula("(SELECT count(*) FROM ANSWER a WHERE a.QUESTION_ID = ID)")
    private Long countOfAnswers;

    @Formula("(SELECT count(*) FROM ANSWER a WHERE a.QUESTION_ID = ID AND a.WRITER_ID = WRITER_ID)")
    private Long countOfAnswersOfWriter;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public User getWriter() {
        return writer;
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

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void update(Question updatedQuestion) {
        title = updatedQuestion.title;
        contents = updatedQuestion.contents;
    }

    public boolean isSameUser(User user) {
        return writer.equals(user);
    }

    private boolean isEmptyAnswers() {
        return countOfAnswers<=0;
    }

    private boolean isAllAnswersByWriter() {
        return countOfAnswers.equals(countOfAnswersOfWriter);
    }

    //댓글이 없거나 모든 댓글이 글 작성자의 것이면 true 반환
    public boolean canDeleteAnswers() {
        if(isEmptyAnswers()) {
            return true;
        }

        return isAllAnswersByWriter();
    }

    public void addCount() {
        this.countOfAnswer += 1;
    }

    public void deleteCount() {
        this.countOfAnswer -= 1;
    }

    public void delete(){
        this.deleted = true;
    }

}
