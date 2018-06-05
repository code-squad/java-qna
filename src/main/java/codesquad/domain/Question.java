package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_id"))
    @JsonProperty
    private User writer;

    @JsonProperty
    @Column(nullable = false)
    private String title;

    @JsonProperty
    private String contents;
    // answersCount를 인스턴스 변수를 쓰지 않고 나타낼 방법은 없는가?
    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    private LocalDateTime createDate;
    private int answersCount;
    private boolean deleted = false;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }



    public void delete() {
        if (hasOtherUserAnswers()) {
            throw new IllegalStateException("다른 사용자가 작성한 답변이 포함된 글은 삭제할 수 없습니다.");
        }
        deleteAllAnswers();
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }

    public boolean isMatchedUserId(User otherUser) {
        if (otherUser == null) {
            throw new NullPointerException("user.null");
        }

        return writer.equals(otherUser);
    }

    @Override
    public String toString() {
        return writer + " " + title + " " + contents;
    }

    public boolean update(Question updateQuestion, User otherUser) {
        if (updateQuestion.title == null || otherUser == null) {
            return false;
        }
        isMatchedUserId(otherUser);

        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;

        return true;
    }

    public void increaseAnswersCount() {
        answersCount++;
    }

    public void decreaseAnswersCount() {
        if (answersCount > 0) {
            answersCount--;
            return;
        }
        throw new IllegalStateException("question.answer.count");
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss"));
    }

    public boolean hasOtherUserAnswers() {
        for (Answer answer : answers) {
            if (answer.isDeleted()) {
                continue;
            }
            if (!answer.isMatchedUserId(writer)) {
                return true;
            }
        }
        return false;
    }

    private void deleteAllAnswers() {
        for (Answer answer : answers) {
            if (answer.isDeleted()) {
                continue;
            }
            answer.delete();
        }
    }
}