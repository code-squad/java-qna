package com.codessquad.qna.controller;

import com.codessquad.qna.exception.NoSuchUserException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.ErrorMessages;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return Paths.USER_LIST_TEMPLATE;
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        User user = findUser(id);
        model.addAttribute("user", user);
        return Paths.USER_PROFILE_TEMPLATE;
    }

    @GetMapping("/editForm")
    public String showUpdatePage(Model model, HttpSession session) {
        User authorizedUser = HttpSessionUtil.getUserFromSession(session);
        User user = findUser(authorizedUser.getId());
        model.addAttribute("user", user);
        return Paths.USER_EDIT_TEMPLATE;
    }

    @PostMapping
    public String createUser(User user) {
        if (!user.isCorrectFormat(user))
            throw new NoSuchUserException(Paths.BAD_REQUEST, ErrorMessages.WRONG_FORMAT);
        userRepository.save(user);
        return Paths.USER_LIST;
    }

    @PutMapping
    public String updateUser(User updateData, String currentPassword, HttpSession session) {
        User authorizedUser = HttpSessionUtil.getUserFromSession(session);
        User user = findUser(authorizedUser.getId());
        user.update(updateData, currentPassword);
        userRepository.save(user);
        return Paths.USER_LIST;
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchUserException(Paths.NOT_FOUND, ErrorMessages.NOTFOUND_USER));
    }
}

