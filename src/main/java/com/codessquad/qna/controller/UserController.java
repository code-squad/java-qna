package com.codessquad.qna.controller;

import com.codessquad.qna.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        for (User user: users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "/";
    }

    @GetMapping("/{index}/edit")
    public String updateUser(@PathVariable int index, Model model) {
        User user = users.get(index);
        model.addAttribute("user", user);
        model.addAttribute("index", index);
        return "user/edit";
    }

    @PostMapping
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @PutMapping("/{index}")
    public void updateUser(@PathVariable int index, User user, String currentPassword, HttpServletResponse response) throws IOException {
        String userPassword = users.get(index).getPassword();
        if (userPassword.equals(currentPassword)) {
            users.remove(index);
            users.add(user);
            response.sendRedirect("/users");
            return;
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('비밀번호가 틀렸습니다'); history.go(-1);</script>");
        out.flush();
    }
}
