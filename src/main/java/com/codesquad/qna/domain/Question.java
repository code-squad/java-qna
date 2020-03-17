package com.codesquad.qna.domain;

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

    @Lob
    private String contents;

    private LocalDateTime writtenTime;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    private int answersNum;

    private boolean deleted;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersNum() {
        answersNum = answers.size();
        return answersNum;
    }


    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writtenTime = LocalDateTime.now();
        this.deleted = false;
    }

    public void updateQuestion(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean answerWriterCheck() {
        boolean sameWriter = true;
        if (answers.isEmpty()) {
            return false;
        }
        Long questionWriterId = this.writer.getId();
        for (int i = 0; i < answers.size(); i++){
            Answer answer = answers.get(i);
            if(!answer.writerCheck(questionWriterId)){
                return true;
            }
        }
        return false;
    }

    public void deletQuestion() {
        this.deleted = true;
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

    public Long getId() {
        return id;
    }

    public void setQuestionIndex(Long questionIndex) {
        this.id += questionIndex;
    }

    public boolean authorizeUser(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", now ='" + writtenTime + '\'' +
                '}';
    }
}
