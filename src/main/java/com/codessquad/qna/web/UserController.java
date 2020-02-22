package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping("")
    public String listPage(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users/list";
    }

    @GetMapping("/new")
    public String createFormPage() {
        return "users/createForm";
    }

    @PostMapping("")
    public String createUser(User user) {
        repository.save(user);
        return "redirect:/users";
    }
}
