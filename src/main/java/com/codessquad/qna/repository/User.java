package com.codessquad.qna.repository;

import com.codessquad.qna.exception.WrongFormatException;
import com.codessquad.qna.util.ErrorMessages;
import com.codessquad.qna.util.Paths;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
@Getter
@Setter
public class User extends AbstractEntity{

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public void update(User updateData, String currentPassword) {
        if (!isCorrectPassword(currentPassword))
            throw new WrongFormatException(Paths.BAD_REQUEST, ErrorMessages.WRONG_PASSWORD);
        if (!isCorrectFormat(updateData))
            throw new WrongFormatException(Paths.BAD_REQUEST, ErrorMessages.WRONG_FORMAT);
        this.password = updateData.password;
        this.name = updateData.name;
        this.email = updateData.email;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isCorrectFormat(User user) {
        if (ObjectUtils.isEmpty(userId))
            return false;

        if (ObjectUtils.isEmpty(user.password))
            return false;

        if (ObjectUtils.isEmpty(user.name))
            return false;

        if (ObjectUtils.isEmpty(user.email))
            return false;

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }
}
