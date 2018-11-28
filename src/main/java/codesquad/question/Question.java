package codesquad.question;

import codesquad.answer.Answer;
import codesquad.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_user"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Answer> answers;

    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    private boolean deleted;

    public Question() {

    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.deleted = false;
    }

    void update(User writer, Question updatedQuestion) {

        //예외처리 도메인에서 로직구현?



        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }

    void delete() {

        //예외처리 도메인에서 로직구현?



        this.deleted = true;

        if(this.answers != null) {
            for (Answer answer : this.answers) {
                answer.delete();
            }
        }
    }

    boolean isMatchWriter(User target) {
        return this.writer.equals(target);
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
