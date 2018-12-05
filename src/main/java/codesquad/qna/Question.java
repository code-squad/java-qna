package codesquad.qna;

import codesquad.answer.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @Column(nullable = false, length = 50)
    @JsonProperty
    private String title;

    @Lob
    @JsonProperty
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

    private LocalDateTime createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    private boolean deleted = false;

//    public int getSizeAnswers(){
//        return answers.size();
//    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers.stream().filter(x -> !x.isDeleted()).collect(Collectors.toList());
    }

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
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
        return this.id;
    }

    public void setId(Long index) {
        this.id = index;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(Integer countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void update(Question updateQuestion, User loginUser) {
        if (isSameWriter(loginUser)) {
            this.title = updateQuestion.title;
            this.contents = updateQuestion.contents;
        }
    }

    public boolean isSameWriter(User sessionedUser) {
        return writer.matchUser(sessionedUser);
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void increaseAnswerCount() {
        ++countOfAnswer;
    }

    public void decreaseAnswerCount() {
        --countOfAnswer;
    }

    public boolean isDelete() {
        if (answers.size() == 0) {          //삭제가능
            return deleted = true;
        }

        for (Answer answer : answers) {     //삭제가능
            if (!answer.isSameWriter(writer)) {         // -아니 달라.
                return deleted = false;
            }
            answer.changeDeletedTrue();                 // -응 작성자와 댓글단 사람이 같아
        }

        return deleted = true;
    }
}
