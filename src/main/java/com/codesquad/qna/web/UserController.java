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

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users/list";
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

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(null);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/{userId}")
    public String read(@PathVariable String userId, Model model) {
        for (User user : userRepository.findAll()) {
            if (userId.equals(user.getUserId())) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "/error/notFound.html";
    }
}
