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

    public Answer() { }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
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

    @Override
    public String toString() {
        return "[Answer ID] " + id + "\n [Writer] " + writer;
    }
}
