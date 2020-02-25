package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private List<User> users = new ArrayList<>();

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/create")
    public String create(User user) {
        user.setId((long) users.size() + 1);
        users.add(user);
//        userRepository.save()
        return "redirect:/users";
    }

    @GetMapping("")
    public String users(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User each : users) {
            if (each.getUserId().equals(userId)) model.addAttribute("user", each);
        }
        return "user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable int id, Model model) {
        for (User each : users) {
            if (each.getId() == id) model.addAttribute("user", each);
        }
        return "user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id,
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
