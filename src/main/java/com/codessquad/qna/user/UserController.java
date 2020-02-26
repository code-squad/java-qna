package com.codessquad.qna.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users/create")
    public String create(User user) {
        System.out.println("User: " + user);
        users.add(user);
        return "index";
    }
}
