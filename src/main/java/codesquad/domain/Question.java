package codesquad.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    private String title;
    @Lob
    private String contents;
    // Lob이라는 어노테이션을 추가하면 255이상으로 작성이 가능하다.

    private LocalDateTime createDate;
    // JPA에서는 매핑을 할때 인자를 받는 생성자와 기본 생성자를 같이 만들어야한다.

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Question() {}

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (createDate == null)
            return "";
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }
}
