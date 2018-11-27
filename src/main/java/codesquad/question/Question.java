package codesquad.question;

import codesquad.config.HttpSessionUtils;
import codesquad.question.answer.Answer;
import codesquad.user.User;
import codesquad.utils.TimeFormatter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User user;

    @CreationTimestamp
    private LocalDateTime createDate;

    @JoinColumn(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Question() {
    }

    public Question(long id, String title, String contents, User user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSameUser(User sessionedUser) {
        return user.equals(sessionedUser);
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getFormattedCreateDate() {
        return TimeFormatter.commonFormat(createDate);
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<Answer> getNotDeletedAnswers() {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public long getNotDeletedAnswersSize() {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .count();
    }

    public boolean isDeleted() {
        return deleted;
    }

    Result deleted(HttpSession session) {
        Result result = valid(session);
        if(!result.isValid()) return result;
        if(!canDeleteAnswer()) {
            return Result.fail("다른 유저의 답변이 있어 질문을 삭제할 수 없습니다");
        }
        this.deleted = true;
        setAnswersDeleted();
        return Result.ok();
    }

    private void setAnswersDeleted() {
        for (Answer answer : answers) {
            answer.deleted();
        }
    }

    boolean canDeleteAnswer() {
        for (Answer answer : answers) {
            if (!answer.isSameUser(this.user)) return false;
        }
        return true;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    Result update(Question updatedQuestion, HttpSession session) {
        Result result = valid(session);
        if(!result.isValid()) return result;
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
        return Result.ok();
    }

    Result valid(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!isSameUser(sessionedUser)) {
            return Result.fail("다른 사람의 글을 수정 또는 삭제할 수 없습니다.");
        }
        return Result.ok();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", user='" + user + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
