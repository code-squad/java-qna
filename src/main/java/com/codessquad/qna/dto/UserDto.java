package com.codessquad.qna.dto;

import com.codessquad.qna.User;

public class UserDto {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userId = user.getUserId();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


}
