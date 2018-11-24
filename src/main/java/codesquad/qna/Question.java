package codesquad.qna;

import codesquad.answer.Answer;
import codesquad.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question(){}

    public Question(User writer,String title,String contents){
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

    public void update(Question updateQuestion) {
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    public boolean matchId(User sessionedUser) {
       return writer.matchUser(sessionedUser);
    }


    public String getFormattedCreateDate(){
        if (createDate == null){
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

}
