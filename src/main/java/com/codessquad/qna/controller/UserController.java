package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        User user = userRepository.getOne(id);
        if (userRepository.existsById(id)) {
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "error/user/notFoundUser";
    }

    @GetMapping("/{id}/edit")
    public String showUpdatePage(@PathVariable Long id, Model model) {
        if (userRepository.existsById(id)){
            model.addAttribute("user", userRepository.getOne(id));
            return "user/edit";
        }
        return "error/user/notFoundUser";
    }

    @PostMapping
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, User updateUser, String currentPassword, HttpServletResponse response) throws IOException {
        User user = userRepository.getOne(id);
        String userPassword = user.getPassword();
        if (userPassword.equals(currentPassword)) {
            user.update(updateUser);
            userRepository.save(user);
            response.sendRedirect("/users");
            return;
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('비밀번호가 틀렸습니다'); history.go(-1);</script>");
        out.flush();
    }
}
