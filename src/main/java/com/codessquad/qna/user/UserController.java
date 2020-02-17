package com.codessquad.qna.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/user/form")
    public String goUserForm() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String createUser(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");

        User newUser = new User(userId, userPassword, userName, userEmail);
        users.add(newUser);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

}
