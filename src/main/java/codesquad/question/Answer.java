package codesquad.question;

import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long index;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_commenter"))
    private User commenter;
    private String contents;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
