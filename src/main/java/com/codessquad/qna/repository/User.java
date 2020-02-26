package com.codessquad.qna.repository;

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
    private String password;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String email;

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
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
}
