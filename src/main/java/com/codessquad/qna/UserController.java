package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @GetMapping("/loginForm")
    public String login() {
        return "userDir/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            return "redirect:/users/loginForm";
        }
        if (!password.equals(user.getPassword())) {
            return "redirect:/users/loginForm";
        }

        session.setAttribute("user", user);
        



        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "userDir/form";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/userDir/list";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "/userDir/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        User user = userRepository.findById(id).get();
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }




}
