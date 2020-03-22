package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class ApiUserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/users/{id}")
    public User show(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
