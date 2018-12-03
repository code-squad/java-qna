package codesquad.question;

import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long index;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_commenter"))
    private User commenter;
    @Column(length = 255)
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_answer"))
    private Question question;
    private boolean deleted = false;

    public Answer(){}

    public Answer(long index, User commenter, String contents, Question question, boolean deleted) {
        this.index = index;
        this.commenter = commenter;
        this.contents = contents;
        this.question = question;
        this.deleted = deleted;
    }

    public Answer(User loginUser, Question question, String contents) {
        this.commenter = loginUser;
        this.question = question;
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isSameWriter(User user) {
        if (this.commenter == null) {
            return false;
        }
        return this.commenter.equals(user);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    void delete() {
        this.deleted = true;
    }
}
