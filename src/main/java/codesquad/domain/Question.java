package codesquad.domain;

import codesquad.web.HttpSessionUtils;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    //    private String writer;
    private String title;
    private String contents;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Question() {

    }

//    public String getWriterId() {
//        return writerId;
//    }
//
//    public void setWriterId(String writerId) {
//        this.writerId = writerId;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getWriter() {
//        return writer;
//    }
//
//    public void setWriter(String writer) {
//        this.writer = writer;
//    }

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

    public boolean matchUser(User user) {
        return writer.equals(user);
    }

    public void update(Question updatedQuestion, HttpSession session) {
        checkEqualSession(session);

        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public void checkEqualSession(HttpSession session) {
        User userFromSession = HttpSessionUtils.getUserFromSession(session);
        if (!matchUser(userFromSession)) {
            throw new IllegalStateException("Don't manipulate Other's contents");
        }
    }
}
