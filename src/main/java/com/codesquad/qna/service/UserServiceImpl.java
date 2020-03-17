package com.codesquad.qna.service;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.exception.UnauthorizedException;
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
    public void updateById(Long id, User updatedUser) {
        User user = findById(id);
        user.update(updatedUser);
        save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UnauthorizedException::noMatchUser);
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
    }
}
