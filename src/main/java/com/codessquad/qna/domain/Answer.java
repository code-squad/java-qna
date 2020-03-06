package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey =  @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey =  @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;
    private LocalDateTime createdDate;

    public Answer() {}

    public Answer(User writer,Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getWriter() { return writer; }
    public String getContents() { return contents; }

    public void setId(Long id) { this.id = id; }
    public void setWriter(User writer) { this.writer = writer; }
    public void setContents(String contents) { this.contents = contents; }

    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return "";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) &&
                Objects.equals(writer, answer.writer) &&
                Objects.equals(contents, answer.contents) &&
                Objects.equals(createdDate, answer.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Answer {" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
