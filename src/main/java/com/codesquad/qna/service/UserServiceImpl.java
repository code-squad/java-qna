package com.codesquad.qna.service;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void updateById(int id, User updatedUser) {
        User user = findById(id);
        user.update(updatedUser);
        save(user);
    }
}
