package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/users")
    public String create(User user) {
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/users/login")
    public String login(String userId, String password, HttpSession session) {
        System.out.println("hi");
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            System.out.println("Login Failure1!");
            return "redirect:/users/loginForm";
        }

        if (!password.equals(user.getPassword())) {
            System.out.println("Login Fauilure2!");
            return "redirect:/users/loginForm";
        }

        System.out.println("Login Success");
        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/users/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Object sessionedUser = session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/updateform";
    }

    @PutMapping("/users/{id}")
    public String update(@PathVariable Long id, Model model, User newUser) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
