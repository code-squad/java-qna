package com.codessquad.qna.controller;

import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.domain.UserRepository;
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
    public UserDto show(@PathVariable Long id) {
        return new UserDto(userRepository.getOne(id));
    }
}
