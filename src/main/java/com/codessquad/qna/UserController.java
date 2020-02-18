package com.codessquad.qna;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String signUp(User newUser) {
        users.add(newUser);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listsOfUsers(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", findUser(userId));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", findUser(userId));
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String updateUser(@PathVariable("userId") String userId, User updatedUser) {
        User originUser = findUser(userId);
        if (!confirmPassword(originUser.getPassword(), updatedUser.getPassword())) {
            return "user/update_failed";
        }
        updateUserData(originUser, updatedUser);
        return "redirect:/users";
    }

    private User findUser(String userId) throws UserNotFoundException {
        for (User user : users) {
            if (userId.equals(user.getUserId())) {
                return user;
            }
        }
        throw new UserNotFoundException("the user is not found.");
    }

    private boolean confirmPassword(String originPassword, String inputPassword) {
        return originPassword.equals(inputPassword);
    }

    private void updateUserData(User originUser, User updatedUser) {
        originUser.setName(updatedUser.getName());
        originUser.setEmail(updatedUser.getEmail());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
