package com.codessquad.qna;

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

    @GetMapping("/form")
    public String viewUserForm() {
        return "/users/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String viewLoginForm() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        try {
            User user = userRepository.findByUserId(userId);
            if (!user.isPasswordEquals(password)) {
                return "redirect:/users/loginForm";
            }
            session.setAttribute("sessionedUser", user);
            return "redirect:/";
        } catch (NullPointerException e) {
            // userId not found
            return "redirect:/users/loginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @GetMapping("")
    public String viewList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("user", getSessionedUser(id, session));
        } catch (NullPointerException | IllegalAccessException e) {
            System.out.println("error code: >>>> "+e.toString());
            return e.getMessage();
        }
        return "/users/profile";
    }

    @GetMapping("/{id}/form")
    public String viewUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            if (!HttpSessionUtils.isLogin(session)) {
                throw new NullPointerException();
            }
            User sessionedUser = HttpSessionUtils.getUserFromSession(session);
            if (!sessionedUser.isIdEquals(id)) {
                throw new IllegalAccessException();
            }
            model.addAttribute("user", sessionedUser);
        } catch (NullPointerException | IllegalAccessException e) {
            System.out.println("error code: >>>> "+e.toString());
            return e.getMessage();
        }
        return "/users/updateForm";
    }

    @PutMapping("/{id}/update")
    public String viewUpdatedList(@PathVariable Long id, String password, String name, String email) {
        User user = userRepository.findById(id).get();
        if (!user.isPasswordEquals(password)) {
            return "redirect:/users";
        }
        userRepository.findById(id).get().setName(name);
        userRepository.findById(id).get().setEmail(email);
        userRepository.save(userRepository.findById(id).get());
        return "redirect:/users";
    }

    private User getSessionedUser(Long id, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isLogin(session)) {
            throw new NullPointerException();
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isIdEquals(id)) {
            throw new IllegalAccessException();
        }
        return sessionedUser;
    }
}
