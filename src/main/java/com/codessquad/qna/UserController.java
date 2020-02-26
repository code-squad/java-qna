package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        Optional<User> matchedUser = userRepository.findById(id);
        if (matchedUser.isPresent()) {
            model.addAttribute("user", matchedUser.get());
            return "/users/profile";
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    public String update(@PathVariable("id") Long id, User updatedUser, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalAccessException("자신의 정보만 수정할 수 있습니다.");
        }

        User user = userRepository.getOne(sessionedUser.getId());

        if (user.matchPassword(updatedUser.getPassword())) {
            user.update(updatedUser);
            userRepository.save(user);
        }

        return "redirect:/users";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalAccessException("자신의 정보만 수정할 수 있습니다.");
        }

        User user = userRepository.getOne(sessionedUser.getId());
        model.addAttribute("user", user);

        return "/users/updateForm";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            System.out.println("Login Failure");
            return "redirect:/login";
        }
        if (!user.matchPassword(password)) {
            System.out.println("Login Failure");
            return "redirect:/login";
        }
        System.out.println("Login Success!");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
