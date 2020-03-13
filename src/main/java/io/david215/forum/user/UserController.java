package io.david215.forum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        User user = findUser(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userId}/update")
    public String updateForm(Model model, @PathVariable String userId) {
        User user = findUser(userId);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/users/{userId}")
    public String update(Model model, @PathVariable String userId, User newUserInfo, String oldPassword) {
        User user = findUser(userId);
        if (oldPassword.equals(user.getPassword())) {
            user.updateUser(newUserInfo);
            userRepository.save(user);
            return "redirect:/users";
        } else {
            model.addAttribute("userId", userId);
            return "user/update-failed";
        }
    }

    private User findUser(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        return optionalUser.orElseThrow(() -> new UserNotFoundException(userId));
    }

    @ExceptionHandler(UserNotFoundException.class)
    private String handleUserNotFoundException(Model model, UserNotFoundException e) {
        model.addAttribute("userId", e.getMessage());
        return "user/not-found";
    }

    private static class UserNotFoundException extends RuntimeException {
        private UserNotFoundException(String message) {
            super(message);
        }
    }
}
