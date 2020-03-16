package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    @Lob
    private String contents;
    private LocalDateTime time;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    private int answerSize;

    private boolean deleted;

    public Question() {

    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now();
        this.answerSize = 0;
        this.deleted = false;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Long getId() {
        return id;
    }

    public int getAnswerSize() {
        return answers.size();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public void updateQuestion(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getFormattedCreateDate() {
        if (time == null) {
            return "";
        }
        return time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public void addAnswerSize() {
        this.answerSize += 1;
    }

    public void deleteQuestion() {
        this.deleted = true;
    }
}
