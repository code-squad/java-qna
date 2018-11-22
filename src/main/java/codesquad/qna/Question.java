package codesquad.qna;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @Column(nullable=false, length=20)
    private String writer;
    @Column(nullable=false, length=100)
    private String title;
    @Column(nullable=false, length=1000)
    private String contents;

    private Question() {}

    private Question(String writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public static Question newInstance(String writer, String title, String contents) {
        return new Question(writer, title, contents);
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
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

    public void update(Question updatedQuestion) {
        setTitle(updatedQuestion.getTitle());
        setContents(updatedQuestion.getContents());
    }
}
