package com.codessquad.qna;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @NotEmpty
    private String title;

    @NotEmpty
    private String contents;

//    @OneToMany(mappedBy = "question")
//    @OrderBy("id ASC")
//    private List<Answer> answers;

    private LocalDateTime postingTime;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.postingTime = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() { return id; }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getPostingTime() {
        return postingTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public boolean isWriterEquals(User sessionUser) {
        return sessionUser.getId().equals(writer.getId());
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "writer: " + writer + "\ntitle: " + title + "\ncontents: " + contents +
                "\npostingTime: " + postingTime + "\n";
    }
}
