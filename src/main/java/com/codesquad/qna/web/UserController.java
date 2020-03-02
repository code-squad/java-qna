package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @GetMapping("/{userId}")
    public String read(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (userId.equals(user.getUserId())) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "/error/notFound.html";
    }
}
