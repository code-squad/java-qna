package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return PathUtil.USER_LIST_TEMPLATE;
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return PathUtil.USER_PROFILE_TEMPLATE;
        }
        return PathUtil.NOT_FOUND;
    }

    @GetMapping("/editForm")
    public String showUpdatePage(Model model, HttpSession session) {
        User authorizedUser = HttpSessionUtil.getUserFromSession(session);
        if (authorizedUser == null) {
            return PathUtil.UNAUTHORIZED;
        }

        Optional<User> user = userRepository.findById(authorizedUser.getId());
        if (user.isPresent()){
            model.addAttribute("user", user.get());
            return PathUtil.USER_EDIT_TEMPLATE;
        }
        return PathUtil.NOT_FOUND;
    }

    @PostMapping
    public String createUser(User user) {
        if (user.isCorrectFormat(user)) {
            userRepository.save(user);
            return PathUtil.USER_LIST;
        }
        return PathUtil.BAD_REQUEST;
    }

    @PutMapping
    public String updateUser(User updateUser, String currentPassword, HttpSession session) {
        User authorizedUser = HttpSessionUtil.getUserFromSession(session);
        if (authorizedUser == null) {
            return PathUtil.UNAUTHORIZED;
        }
        Optional<User> originUser = userRepository.findById(authorizedUser.getId());
        if (originUser.isPresent()) {
            User origin = originUser.get();
            return update(origin, updateUser, currentPassword);
        }
        return PathUtil.NOT_FOUND;
    }

    private String update(User user, User updateData, String currentPassword) {
        boolean isCorrectPassword = user.isCorrectPassword(currentPassword);
        boolean isCorrectFormat = user.isCorrectFormat(updateData);

        if (!isCorrectFormat) {
            return PathUtil.BAD_REQUEST;
        }
        if (!isCorrectPassword) {
            return PathUtil.UNAUTHORIZED;
        }

        user.update(updateData);
        userRepository.save(user);
        return PathUtil.USER_LIST;
    }
}

