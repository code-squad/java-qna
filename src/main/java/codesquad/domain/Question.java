package codesquad.domain;

import javax.persistence.*;

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

    private String tag;


    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return writer + " " + title + " " + contents;
    }

    public boolean isMatchedWriter(String userId) {
        return writer.isMatchedUserId(userId);
    }

    public boolean update(Question updateQuestion) {
        if (updateQuestion.title == null) {
            return false;
        }
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;

        return true;
    }
}
