package com.codesquad.qna.service;

import com.codesquad.qna.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);

    void update(Long id, User loginUser, User updatedUser);

    User findByUserId(String userId);

    User findById(Long id);
}
