package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String createUser(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/form")
    public String goForm() {
        return "user/form";
    }

    @GetMapping("/login")
    public String goLoginForm() {
        return "user/login";
    }

    @GetMapping("/{id}")
    public String showUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String modifyUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", userRepository.findById(id).get());

        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUserProfile(@PathVariable Long id, Model model, User updateUser) {
        User oldUser = userRepository.findById(id).get();
        if (oldUser.isCheckPassword(updateUser)) {
            oldUser.update(updateUser);
            userRepository.save(oldUser);
        }
        model.addAttribute("userProfile", oldUser);

        return "redirect:/users";
    }
}
