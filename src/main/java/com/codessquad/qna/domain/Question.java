package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OrderBy("id DESC")
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @Column(nullable = false)
    private boolean deleted = false;

    @JsonProperty
    private Integer countOfAnswer = 0;

    public Question() {
        markCreatedTime();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return createdAt;
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

    public int getCountOfAnswer() {
        return this.countOfAnswer;
    }

    private void markCreatedTime() {
        this.createdAt = LocalDateTime.now();
    }

    public void update(Question question) {
        if (!isValid(question)) {
            throw new RuntimeException("InvalidUpdateQuestion");
        }
        this.contents = question.contents;
        this.title = question.title;
    }

    private boolean isValid(Question question) {
        return question.contents != null && question.title != null;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void delete() {
        this.deleted = true;
        this.deleteAnswers();
    }

    private void deleteAnswers() {
        this.answers.forEach(Answer::delete);
    }

    public boolean canDelete() {
        if (this.answers.size() == 0) {
            return true;
        }

        return this.answers.stream().map(answer -> answer.getWriter().equals(this.writer)).reduce(true, (acc, isSameWriter) -> acc && isSameWriter);
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id=" + id +
                '}';
    }
}
