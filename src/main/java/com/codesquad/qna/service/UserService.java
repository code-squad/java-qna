package com.codesquad.qna.service;

import com.codesquad.qna.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);

    void updateById(Long id, User user);

    User findByUserId(String userId);

    User findById(Long id);
}
