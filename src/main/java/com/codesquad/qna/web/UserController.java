package com.codesquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {
    List<User> userList = new ArrayList<>();

    @PostMapping("/users")
    public String createUser(User user) {
        long id = userList.size() + 1;

        user.setId(id);
        System.out.println(user);
        userList.add(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String printUsers(Model model) {
        model.addAttribute("users", userList);

        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showUser(@PathVariable String userId, Model model) {
        User selectedUser = null;

        for (User user : userList) {
          if (user.getUserId().equals(userId)) {
              selectedUser = user;
              break;
          }
        }

        model.addAttribute("user", selectedUser);

        return "profile";
    }

    @GetMapping("/users/{id}/form")
    public String userForm(@PathVariable long id, Model model) {
        User selectedUser = userList.get((int)id - 1);
        model.addAttribute("user", selectedUser);

        return "user/updateForm";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable long id, User updatedUser) {
        int index = (int)id - 1;
        User selectedUser = userList.get(index);
        System.out.println(selectedUser);
        userList.set(index, updatedUser);
        System.out.println(updatedUser);

        return "redirect:/users";
    }
}
