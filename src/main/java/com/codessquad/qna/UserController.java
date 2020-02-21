package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        Optional<User> matchedUser = userRepository.findById(id);
        if (matchedUser.isPresent()) {
            model.addAttribute("user", matchedUser.get());
            return "/users/profile";
        }
        return "redirect:/users";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, User updateUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(updateUser.getPassword())) {
            User user = optionalUser.get();
            user.update(updateUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        String userId = userRepository.getOne(id).getUserId();
        model.addAttribute("userId", userId);
        return "/users/updateForm";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }
}
