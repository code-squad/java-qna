package com.codesquad.qna.domain;

import com.codesquad.qna.web.DateTimeFormatConstants;
import org.hibernate.annotations.Formula;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @Lob
    @NotEmpty
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @OneToMany(mappedBy="question") //Answer에서 Question을 매핑할 때 설정한 필드 이름으로 주면됨
    @OrderBy("id ASC")
    private List<Answer> answers;

    @Formula("(select count(*) from answer a where a.question_id = id and a.deleted = false)")
    private int countOfAnswers;

    @Column(nullable = false)
    private boolean deleted = false;

    public Question() {
        setCreatedTimeNow();
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        setCreatedTimeNow();
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getCountOfAnswers() {
        return countOfAnswers;
    }

    public void setCountOfAnswers(int countOfAnswers) {
        this.countOfAnswers = countOfAnswers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setCreatedTimeNow() {
        setCreatedTime(LocalDateTime.now());
    }

    public String getFormattedCreatedTime() {
        return this.createdTime.format(DateTimeFormatConstants.BOARD_DATA_DATE_TIME_FORMAT);
   }

    public void update(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
        setCreatedTimeNow(); //수정된 시간으로 업데이트
    }

    public boolean matchUser(User sessionUser) {
        return this.writer.equals(sessionUser);
    }

    public void delete() {
        if (getCountOfAnswers() != 0 && isNotErasable()) {
            throw new IllegalStateException("답변이 달린 게시글은 삭제할 수 없습니다.");
        }

        deleteAnswers(); //남아있는 댓글 delete
        this.deleted = true;
    }

    public boolean isNotErasable() {
        for (Answer answer : answers) {
            if (!answer.matchUser(this.writer)) {
                return true;
            }
        }
        return false;
    }

    public void deleteAnswers() {
        for (Answer answer : answers) {
            answer.delete();
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdTime=" + createdTime +
                ", answers=" + answers +
                ", countOfAnswers=" + countOfAnswers +
                ", deleted=" + deleted +
                '}';
    }
}
