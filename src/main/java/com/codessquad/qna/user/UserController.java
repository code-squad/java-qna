package com.codessquad.qna.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/user/form")
    public String goUserForm(Model model) {
        model.addAttribute("actionUrl", "/user/create");
        model.addAttribute("buttonName", "회원가입");
        return "user/form";
    }

    @PostMapping("/user/create")
    public String createUser(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");

        User newUser = new User(userId, userPassword, userName, userEmail);
        users.add(newUser);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userName}")
    public String showUserProfile(@PathVariable String userName, Model model) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                model.addAttribute("user", user);
            }
        }
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String showUserInfoModifyForm(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("actionUrl", "/users/" + userId + "/update");
        model.addAttribute("buttonName", "수정");
        return "/user/form";
    }

    @PostMapping("/users/{userId}/update")
    public String updateUserInfo(@PathVariable String userId, HttpServletRequest request) {
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        User modifyUser = null;

        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                modifyUser = user;
            }
        }

        if (modifyUser == null || !modifyUser.getUserPassword().equals(userPassword)) {
            return "redirect:/users";
        }

        modifyUser.setUserName(userName);
        modifyUser.setUserEmail(userEmail);
        return "redirect:/users";
    }

}
