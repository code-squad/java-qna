package com.codessquad.qna.repository;

import com.codessquad.qna.exception.CustomWrongFormatException;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.PathUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    @Setter
    private String userId;

    @Column(nullable = false)
    @Setter
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String email;

    public void update(User updateData, String currentPassword) {
        if (!isCorrectPassword(currentPassword))
            throw new CustomWrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_PASSWORD);
        if (!isCorrectFormat(updateData))
            throw new CustomWrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_FORMAT);
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
