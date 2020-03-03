package com.codesquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String createUser(User user) {
        users.add(user);

        return "redirect:/users";
    }
}
