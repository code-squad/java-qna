package io.david215.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    Map<String, User> users = new HashMap<>();

    @PostMapping("/users")
    public String createNewUser(User user) {
        String userId = user.getUserId();
        if (!users.containsKey(userId)) {
            users.put(userId, user);
            return "redirect:/users";
        }
        return "redirect:/signup-fail?userId=" + userId;
    }

    @GetMapping("/signup-fail")
    public String signupFail(Model model, String userId) {
        model.addAttribute("userId", userId);
        return "users/signup-fail";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", users.values());
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            model.addAttribute("user", user);
            return "users/profile";
        }
        return "users/not-found";
    }
}
