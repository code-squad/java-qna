package codesquad.question;

import codesquad.answer.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User writer;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Answer> answers;

    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    private boolean deleted;

    public Question() {
        this.createDate = LocalDateTime.now();
        this.deleted = false;
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.deleted = false;
    }

    boolean isMatchWriter(User target) {
        return this.writer.equals(target);
    }

    void update(Question updatedQuestion) {
        if(!updatedQuestion.isMatchWriter(this.writer)) {
            throw new IllegalStateException("작성자만 수정 가능합니다.");
        }

        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
        this.createDate = updatedQuestion.createDate;
    }

    void delete(User loggedInUser) {
        if(!isMatchWriter(loggedInUser)) {
            throw new IllegalStateException("작성자만 삭제 가능합니다.");
        }

        if(this.answers == null) {
            this.deleted = true;
        }

        if(isAuthorizedAnswers(this.answers)) {
            for (Answer answer : this.answers) {
                answer.delete(loggedInUser);
            }
            this.deleted = true;
        }

        //TODO: else를 쓰지않고 삭제가 불가능한 케이스 알려주기, 다른 작성자의 답변이 남아있을 경우
    }

    private boolean isAuthorizedAnswers(List<Answer> answers) {
        if(answers == null) {
            return false;
        }

        for (Answer answer : answers) {
            if(!answer.isMatchWriter(this.writer)){
                return false;
            }
        }

        return true;
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id &&
                deleted == question.deleted &&
                Objects.equals(writer, question.writer) &&
                Objects.equals(answers, question.answers) &&
                Objects.equals(title, question.title) &&
                Objects.equals(contents, question.contents) &&
                Objects.equals(createDate, question.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, answers, title, contents, createDate, deleted);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                ", deleted=" + deleted +
                '}';
    }
}
