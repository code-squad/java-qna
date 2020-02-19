package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        Optional<User> matchedUser = findUser(userId);
        if (matchedUser.isPresent()) {
            model.addAttribute("user", matchedUser.get());
            return "/users/profile";
        }
        return "redirect:/users";
    }

    @PostMapping("/{userId}/update")
    public String update(@PathVariable("userId") String userId, String password, String name, String email) {
        Optional<User> optionalUser = findUser(userId);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            User user = optionalUser.get();
            updateUser(user, name, password, email);
        }
        return "redirect:/users";
    }

    @GetMapping("/{userId}/update")
    public String updateForm(@PathVariable("userId") String userId, Model model) {
        System.out.println(userId);
        model.addAttribute("userId", userId);
        return "/users/updateForm";
    }

    @PostMapping("/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    private Optional<User> findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private void updateUser(User user, String name, String password, String email) {
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
    }
}
