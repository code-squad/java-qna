package com.codesquad.qna.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    private String contents;

    private LocalDateTime createdDate;

    public Answer() {
        createdDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreatedDate() {
        String dateTimePattern = "yyyy년 MM월 dd일 HH시 mm분 ss초";

        return createdDate.format(DateTimeFormatter.ofPattern(dateTimePattern));
    }

    public boolean isSameWriter(User writer) {
        return this.writer.equals(writer);
    }
}
