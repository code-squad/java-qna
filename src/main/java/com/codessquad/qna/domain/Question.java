package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @OneToMany(mappedBy = "question")
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    //ignore 안하면 에러남
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
    private LocalDateTime writeTime;

    private boolean deleted;

    @Formula("(SELECT count(*) FROM ANSWER a WHERE a.QUESTION_ID = ID)")
    private Long countOfAnswers;

    @Formula("(SELECT count(*) FROM ANSWER a WHERE a.QUESTION_ID = ID AND a.WRITER_ID = WRITER_ID)")
    private Long countOfAnswersOfWriter;

    public List<Answer> getAnswers() {
        return answers;
    }

    public Long getId() {
        return id;
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

    public void setWriteTime(LocalDateTime writeTime) {
        this.writeTime = writeTime;
    }

    public String getWriteTime() {
        return writeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setWriteTimeNow() {
        setWriteTime(LocalDateTime.now());
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

    public void delete(){
        this.deleted = true;
    }

}
