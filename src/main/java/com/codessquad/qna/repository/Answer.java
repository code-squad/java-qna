package com.codessquad.qna.repository;

import com.codessquad.qna.exception.CustomWrongFormatException;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.PathUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Setter
    @Lob
    @Column(nullable = false)
    private String contents;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Answer(){}
    public Answer(User user, Question question, String contents) {
        this.writer = user;
        this.question = question;
        this.contents = contents;
    }

    public void update(Answer updateData) {
        if (!isCorrectFormat(updateData))
            throw new CustomWrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_FORMAT);
        this.contents = updateData.contents;
    }

    public boolean isCorrectFormat(Answer answer) {
        return ObjectUtils.isNotEmpty(answer.contents);
    }

    public boolean isCorrectWriter(User user) {
        return this.writer.equals(user);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0: id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Answer other = (Answer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
