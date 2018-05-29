package codesquad.domain;

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
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_id"))
    private User writer;

    @Column(nullable = false)
    private String title;

    private String contents;
    // answersCount를 만들자니 인스턴스 변수가 늘어난다.
    // helper class를 만드는 방법을 익히자.
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    private LocalDateTime createDate;
    private int answersCount;
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isMatchedUserId(User otherUser) {
        if (otherUser == null) {
            throw new NullPointerException("user.null");
        }
        writer.equals(otherUser);

        return true;
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
}