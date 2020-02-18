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
    public List<User> users = new ArrayList<>();

    @GetMapping("/index.html")
    public String index() {
        return "index";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:list.html";
    }

    @GetMapping("/user/list.html")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/user/profile.html")
    public String profile(Model model) {
        model.addAttribute("users", users);
        return "profile";
    }

    @GetMapping("/user/{userId")
    public String profileName(Model model) {

        return "redirect:/profile";
    }

//    @GetMapping("/user/{userId}")
//    public String profileName(@PathVariable String userId) {
//        String returnName = "";
//        if (userId.equals(""))
//    }

}
