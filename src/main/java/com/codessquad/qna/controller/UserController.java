package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/profile";
        }
        return "redirect:/error/notFound";
    }

    @GetMapping("/{id}/editForm")
    public String showUpdatePage(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            model.addAttribute("user", user.get());
            return "user/edit";
        }
        return "redirect:/error/notFound";
    }

    @PostMapping
    public String createUser(User user) {
        if (isCorrectUserFormat(user)) {
            userRepository.save(user);
            return "redirect:/users";
        }
        return "redirect:/error/badRequest";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updateUser, String currentPassword) {
        Optional<User> originUser = userRepository.findById(id);
        if (originUser.isPresent()) {
            User origin = originUser.get();
            return update(origin, updateUser, currentPassword);
        }
        return "redirect:/error/notFound";
    }

    private String update(User originUser, User updateData, String currentPassword) {
        boolean isCorrectPassword = isCorrectPassword(originUser.getPassword(), currentPassword);
        boolean isCorrectForm = isCorrectUserFormat(updateData);

        if (isCorrectForm) {
            if (isCorrectPassword) {
                originUser.update(updateData);
                userRepository.save(originUser);
                return "redirect:/users";
            }
            return "redirect:/error/unauthorized";
        }
        return "redirect:/error/badRequest";
    }

    private boolean isCorrectUserFormat(User user) {
        boolean userIdIsExist = ObjectUtils.isNotEmpty(user.getUserId());
        boolean nameIsExist = ObjectUtils.isNotEmpty(user.getName());
        boolean passwordIsExist = ObjectUtils.isNotEmpty(user.getPassword());
        boolean emailIsExist = ObjectUtils.isNotEmpty(user.getEmail());

        return userIdIsExist && nameIsExist && passwordIsExist && emailIsExist;
    }

    private boolean isCorrectPassword(String originPassword, String currentPassword){
        return originPassword.equals(currentPassword);
    }

}

