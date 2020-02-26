package io.david215.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    Map<String, User> users = new HashMap<>();

    @PostMapping("/users/new")
    public String createNewUser(User user) {
        String userId = user.getUserId();
        if (users.containsKey(userId)) {
            return "redirect:/user/signup-fail.html";
        }
        users.put(userId, user);
        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    public String users(Model model) {
        model.addAttribute("users", users.values());
        return "users/list";
    }
}
