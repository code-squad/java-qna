package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public String create(User newUser) {
        userRepository.save(newUser);
        log.info("signUp : {}", newUser);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", findUser(userId));
        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", findUser(userId));
        return "users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable String userId, User updateUser) {
        User originUser = findUser(userId);
        if (!confirmPassword(originUser.getPassword(), updateUser.getPassword())) {
            return "users/update_failed";
        }
        originUser.update(updateUser);
        userRepository.save(originUser);
        return "redirect:/users";
    }

    private User findUser(String userId) {
        log.info("findUser: {}", userId);
        return userRepository.findById(userId).get();
    }

    private boolean confirmPassword(String originPassword, String inputPassword) {
        log.info("confirmPassword : {}, {}", originPassword, inputPassword);
        return originPassword.equals(inputPassword);
    }
}
