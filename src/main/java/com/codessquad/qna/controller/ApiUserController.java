package com.codessquad.qna.controller;

import com.codessquad.qna.exception.CustomNoSuchElementException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User show(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new CustomNoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_USER));
    }
}
