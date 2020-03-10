package io.david215.forum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public String createNewUser(User user) {
        String userId = user.getUserId();
        if (!userRepository.existsByUserId(userId)) {
            userRepository.save(user);
            return "redirect:/users";
        }
        return "redirect:/signup-fail?userId=" + userId;
    }

    @GetMapping("/signup-fail")
    public String signupFail(Model model, String userId) {
        model.addAttribute("userId", userId);
        return "user/signup-fail";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "user/not-found";
    }
}
