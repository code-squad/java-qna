package com.codessquad.qna.repository;

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
    @Column(nullable = false)
    private String contents;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Answer(){}
    public Answer(User user, Question question, String contents) {
        this.writer = user;
        this.question = question;
        this.contents = contents;
    }

    public void update(Answer answer) {
        this.writer = answer.writer;
        this.question = answer.question;
        this.contents = answer.contents;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isCorrectFormat(Answer answer) {
        boolean writerIsExist = ObjectUtils.isNotEmpty(answer.writer);
        boolean contentsIsExist = ObjectUtils.isNotEmpty(answer.contents);

        return writerIsExist && contentsIsExist;
    }

    public boolean isCorrectWriter(User user) {
        System.out.println();
        return this.writer.getId().compareTo(user.getId()) == 0;
    }
}
