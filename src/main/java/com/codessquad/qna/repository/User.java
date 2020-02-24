package com.codessquad.qna.repository;

import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false)
    @Setter
    private String userId;

    @Column(nullable = false)
    @Setter
    private String password;

    @Setter
    private String name;

    @Setter
    private String email;

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
    }
}
