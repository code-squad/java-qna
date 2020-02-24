package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users") // 중복되는 prefix, 자원에 대해 명시해준다.
public class UserController {
    private List<User> userList = Collections.synchronizedList(new ArrayList<>());

    @PostMapping("/create")
    public String createUser(User user) {
        userList.add(user);
        System.out.println(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("users", userList);
        return "users/list";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) throws NullPointerException {
        model.addAttribute("user", getUserById(userId));
        return "users/profile";
    }

    public User getUserById(String userId) throws NullPointerException{
        for (User user : userList) {
            if(userId.equals(user.getUserId())){
                return user;
            }
        }
        throw new NullPointerException("null");
    }

}
