package codesquad.domain;

import codesquad.web.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.REMOVE)
    @OrderBy
    private List<Answer> answers;

    private String title;
    @Lob
    private String contents;

    private LocalDateTime updateDate;

    public Question() {
        this.updateDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (updateDate == null) {
            return "";
        }
        return updateDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public Result update(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("You can't update, Please Login");
        }
        User loginedUser = HttpSessionUtils.getSessionedUser(session);
        if (!loginedUser.isSameWriter(question)) {
            return Result.fail("You can't update another user's Question");
        }
        this.title = question.title;
        this.contents = question.contents;
        this.updateDate = LocalDateTime.now();
        return Result.ok();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getNumberOfAnswer() {
        return answers.size();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
