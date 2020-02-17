package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/user/create")
    public String addUser(Model model, String userId, String password, String name, String email) {
        System.out.println("userId : " + userId + " password : " + password);
        model.addAttribute("name", name);
        return "createok"; //templates의 index.html 호출
    }
}
