package codesquad.question;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length=20)
    private String writer;

    @Column(nullable = false, length=100)
    private String title;

    @Lob
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    void update(Question updatedQuestion) {
        this.setTitle(updatedQuestion.getTitle());
        this.setContents(updatedQuestion.getContents());
    }
}
