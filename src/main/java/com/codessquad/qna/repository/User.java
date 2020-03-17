package com.codessquad.qna.repository;

import com.codessquad.qna.exception.WrongFormatException;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.PathUtil;
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
            throw new WrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_PASSWORD);
        if (!isCorrectFormat(updateData))
            throw new WrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_FORMAT);
        this.password = updateData.password;
        this.name = updateData.name;
        this.email = updateData.email;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isCorrectFormat(User user) {
        boolean userIdIsExist = ObjectUtils.isNotEmpty(user.userId);
        boolean nameIsExist = ObjectUtils.isNotEmpty(user.name);
        boolean passwordIsExist = ObjectUtils.isNotEmpty(user.password);
        boolean emailIsExist = ObjectUtils.isNotEmpty(user.email);

        return userIdIsExist && nameIsExist && passwordIsExist && emailIsExist;
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
