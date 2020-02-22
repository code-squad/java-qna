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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @GetMapping("/join")
    public String moveUserForm() {
        return "/users/join";
    }

    @PostMapping("/create")
    public String addUser(User user) {
        userRepository.save(user);
        return "redirect:/users"; //templates의 index.html 호출
    }

    @GetMapping("")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public ModelAndView viewProfile(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("/users/profile");
        mav.addObject("currentUser", userRepository.findById(id).get());
        return mav;
    }

}
