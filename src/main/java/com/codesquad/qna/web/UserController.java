package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public String createUser(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String printUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }
//
//    @GetMapping("/users/{id}")
//    public String showUser(@PathVariable long id, Model model) {
//        User selectedUser = userList.get((int)id - 1);
//        model.addAttribute("user", selectedUser);
//
//        return "user/profile";
//    }
//
//    @GetMapping("/users/{id}/form")
//    public String userForm(@PathVariable Long id, Model model) {
//        User selectedUser = userList.get((int)id - 1);
//        model.addAttribute("user", selectedUser);
//
//        return "user/updateForm";
//    }
//
//    @PostMapping("/users/{id}")
//    public String updateUser(@PathVariable long id, User updatedUser) {
//        int index = (int)id - 1;
//        User selectedUser = userList.get(index);
//        System.out.println(selectedUser);
//        userList.set(index, updatedUser);
//        System.out.println(updatedUser);
//
//        return "redirect:/users";
//    }
}
