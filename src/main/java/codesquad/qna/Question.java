package codesquad.qna;

import codesquad.user.User;

import javax.persistence.*;
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

    public List<Answer> getAnswers() {
        return answers;
    }

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


    public void update(Question modify) {
        this.title = modify.title;
        this.contents = modify.contents;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public Result deleted() {
        for (Answer answer : answers) {
            if(!answer.isSameWriter(this.writer)) {
                return Result.failed("작성 아이디와 일치하지 않은 댓글이 있어 삭제할 수 없습니다.");
            }
        }
        return deleteOk();
    }

    private Result deleteOk() {
        System.out.println("삭제");
        this.deleted = true;
        for (Answer answer : answers) {
            answer.delete();
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
