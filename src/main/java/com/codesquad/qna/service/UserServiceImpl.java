package com.codesquad.qna.service;

import com.codesquad.qna.advice.exception.UnauthorizedException;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void update(Long id, User loginUser, User updatedUser) {
        loginUser.hasPermission(id);
        loginUser.update(updatedUser);
        save(loginUser);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UnauthorizedException::noMatchUser);
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UnauthorizedException::noMatchUser);
    }
}
