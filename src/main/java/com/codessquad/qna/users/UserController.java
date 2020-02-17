package com.codessquad.qna.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users/form")
    public String goUserForm() {
        return "user/form";
    }

}
