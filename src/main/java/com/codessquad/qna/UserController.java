package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String logging(String userId, String password, HttpSession httpSession) {
        User user = userRepository.findByUserId(userId);
        System.out.println("user : " + user);

        if (user == null) {
            return "redirect:/users/loginForm";
        }
        if (!user.getPassword().equals(password)) {
            return "redirect:/users/loginForm";
        }

        httpSession.setAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long id,
                             @RequestParam String password,
                             @RequestParam String newPassword,
                             @RequestParam String checkPassword,
                             @RequestParam String name,
                             @RequestParam String email) {
        User user = userRepository.findById(id).get();
        if (user.getPassword().equals(password)) {
            if (newPassword.equals(checkPassword)) {
                user.update(name, email, newPassword);
                userRepository.save(user);
                return "redirect:/users";
            }
        }
        return "redirect:/users/{id}/form";
    }
}
