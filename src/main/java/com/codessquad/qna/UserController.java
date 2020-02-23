package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();
    @PostMapping("/user/create")
    public String create(User user) {
        user.setId(users.size()+1);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User each : users) {
            if (each.getUserId().equals(userId)) model.addAttribute("user", each);
        }
        return "profile";
    }

    @GetMapping("/user/form")
    public String userForm(){
        return "form";
    }

    @GetMapping("/users/{id}/form")
    public String updateUser(@PathVariable int id, Model model) {
        for (User each : users) {
            if (each.getId() == id) model.addAttribute("user", each);
        }
        return "updateForm";
    }

    @PostMapping("/user/{id}/update")
    public String checkUpdateVaildate(@PathVariable int id, Model model, String password, String newPassword, String checkPassword, String name, String email){
        System.out.println("password : " +password+ " newPassword : " + newPassword + " checkPassword : "+ checkPassword);
        User user = new User();
        for (User each : users) {
            if (each.getId() == id) user = each;
        }
        if (user.getPassword().equals(password)){
            if (newPassword.equals(checkPassword)) {
                user.setPassword(newPassword);
                user.setName(name);
                user.setEmail(email);
                return "redirect:/users";
            }
        }
        return "redirect:/users/{id}/form";
    }
}
