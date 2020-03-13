package com.codessquad.qna.repository;

import com.codessquad.qna.exception.WrongFormatException;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.PathUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Answer extends AbstractEntity{

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Lob
    @Column(nullable = false)
    private String contents;
    private boolean deleted = false;

    public Answer(){}
    public Answer(User user, Question question, String contents) {
        this.writer = user;
        this.question = question;
        this.contents = contents;
    }

    public void update(Answer updateData) {
        if (!isCorrectFormat(updateData))
            throw new WrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_FORMAT);
        this.contents = updateData.contents;
    }

    public boolean isCorrectFormat(Answer answer) {
        return ObjectUtils.isNotEmpty(answer.contents);
    }

    public boolean isCorrectWriter(User user) {
        return this.writer.equals(user);
    }
}
