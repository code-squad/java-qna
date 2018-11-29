package codesquad.qna;

import codesquad.user.HttpSessionUtils;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    @Column(nullable = false)
    private String title;

    @Lob
    private String contents;

    private LocalDateTime timer;

    @Column(nullable = false)
    private boolean deleted;

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.timer = LocalDateTime.now();
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

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getFormatTime() {
        if (timer == null) {
            return "";
        }
        return timer.format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm:ss"));
    }

    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
    }

    @JsonIgnore
    public List<Answer> getShowAnswers() {
        return answers.stream().filter(answer -> !answer.isDeleted()).collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getAnswerCount() {
        return answers.size();
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Result update(Question modify, User loginUser) {
        Result result = valid(loginUser);
        if(!result.isValid()) {
            return Result.failed(result.getErrorMessage());
        }
        this.title = modify.title;
        this.contents = modify.contents;
        return Result.ok();
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public Result deleted(User loginUser) {
        Result result = valid(loginUser);
        if(!result.isValid()) {
            return Result.failed(result.getErrorMessage());
        }
        for (Answer answer : answers) {
            if(!answer.isSameWriter(this.writer)) {
                return Result.failed("작성 아이디와 일치하지 않은 댓글이 있어 삭제할 수 없습니다.");
            }
        }
        return deleteOk(loginUser);
    }

    private Result deleteOk(User loginUser) {
        System.out.println("삭제");
        this.deleted = true;
        for (Answer answer : answers) {
            answer.delete(loginUser);
        }
        return Result.ok();
    }

     Result valid(User loginUser) {
        if (loginUser == null) {
            return Result.failed("로그인이 필요합니다.");
        }

        if (!isSameWriter(loginUser)) {
            return Result.failed("자신이 쓴 글만 수정, 삭제할 수 있습니다.");
        }
        return Result.ok();
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
