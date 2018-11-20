package codesquad.question;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @Column(nullable = false, length = 20)
    private String writer;
    @Column(nullable = false, length = 40)
    private String title;
    @Column(nullable = false, length = 10000)
    private String contents;
    @Column(nullable = false)
    private Long id;
    private String time;

    Question() {
    }

    public Question(Long index, String writer, String title, String contents, Long id, String time) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.id = id;
        this.time = time;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean matchId(Long id) {
        return this.id.equals(id);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "index=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id='" + id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

}
