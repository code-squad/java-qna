package io.david215.forum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("userId", userId);
        return "user/not-found";
    }

    @GetMapping("/users/{userId}/update")
    public String updateForm(Model model, @PathVariable String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            return "user/update";
        }
        model.addAttribute("userId", userId);
        return "user/not-found";
    }

    @PostMapping("/users/{userId}")
    public String update(Model model, User newUserInfo, String oldPassword) {
        String userId = newUserInfo.getUserId();
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String password = user.getPassword();
            if (oldPassword.equals(password)) {
                user.setPassword(newUserInfo.getPassword());
                user.setName(newUserInfo.getName());
                user.setEmail(newUserInfo.getEmail());
                userRepository.save(user);
                return "redirect:/users";
            } else {
                model.addAttribute("userId", userId);
                return "user/update-failed";
            }
        }
        model.addAttribute("userId", userId);
        return "user/not-found";
    }
}
