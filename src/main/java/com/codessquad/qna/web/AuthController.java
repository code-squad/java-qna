package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @PostMapping("/login")
    public String authenticate(User entrant, HttpSession session) {
        User originInfo = userRepository.findByAccountId(entrant.getAccountId());

        if(originInfo != null && originInfo.verify(entrant)) {
            session.setAttribute("myId", originInfo.getId());
            session.setMaxInactiveInterval(60*60);
            return "redirect:/";
        }

        return "redirect:/auth/failure";
    }

    @PostMapping("/logout")
    public String leave(HttpSession session, HttpServletResponse response) {
        session.invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
