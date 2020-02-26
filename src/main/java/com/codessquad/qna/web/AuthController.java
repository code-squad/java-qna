package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String loginPage() {
        return "auth/loginForm";
    }

    @GetMapping("/failure")
    public String loginFailPage() {
        return "auth/loginFailForm";
    }

    @PostMapping()
    public String authenticate(@ModelAttribute User entrant) {
        User originInfo = userRepository.findByUserId(entrant.getUserId());

        if(originInfo != null && originInfo.verify(entrant)) {
            return "redirect:/";
        }

        return "redirect:/auth/failure";
    }
}
