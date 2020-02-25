package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        user.setId((long) users.size() + 1);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User each : users) {
            if (each.getUserId().equals(userId)) model.addAttribute("user", each);
        }
        return "profile";
    }

    @GetMapping("/user/form")
    public String userForm() {
        return "form";
    }

    @GetMapping("/users/{id}/form")
    public String updateUser(@PathVariable int id, Model model) {
        for (User each : users) {
            if (each.getId() == id) model.addAttribute("user", each);
        }
        return "updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String checkUpdateVaildate(@PathVariable Long id,
                                      @RequestParam String password,
                                      @RequestParam String newPassword,
                                      @RequestParam String checkPassword,
                                      @RequestParam String name,
                                      @RequestParam String email) {
        System.out.println("password : " + password + " newPassword : " + newPassword + " checkPassword : " + checkPassword);
        User user = new User();
        for (User each : users) {
            if (each.getId().equals(id)) user = each;
        }
        if (user.getPassword().equals(password)) {
            if (newPassword.equals(checkPassword)) {
                user.setPassword(newPassword);
                user.setName(name);
                user.setEmail(email);
                return "redirect:/users";
            }
        }
        return "redirect:/users/{id}/form";
    }
}
