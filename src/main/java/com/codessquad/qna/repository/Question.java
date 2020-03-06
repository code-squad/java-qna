package com.codessquad.qna.repository;


import com.codessquad.qna.exception.CustomWrongFormatException;
import com.codessquad.qna.util.PathUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Lob
    @Column(nullable = false)
    private String contents;
    private LocalDate createdAt = LocalDate.now();

    public Question () {}
    public Question(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.writer = user;
    }

    public void update(Question updateData) {
        if (!isCorrectFormat(updateData)) {
            throw new CustomWrongFormatException(PathUtil.BAD_REQUEST, "입력값을 모두 입력해주세요");
        }
        this.title = updateData.title;
        this.contents = updateData.contents;
    }

    public boolean isCorrectFormat(Question question) {
        boolean titleIsExist = ObjectUtils.isNotEmpty(question.getTitle());
        boolean contentIsExist = ObjectUtils.isNotEmpty(question.getContents());

        return titleIsExist && contentIsExist;
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
        Question other = (Question) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
