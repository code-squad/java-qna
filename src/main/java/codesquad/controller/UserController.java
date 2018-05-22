package codesquad.controller;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public String create(User user) {
        try {
            userRepo.save(user);
            return "redirect:/users";
        } catch (DataAccessException e) {
            logger.error("ERROR {} ", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "/users/list";
    }

    @PostMapping("/login")
    public String logining(String userId, String passwd, HttpSession session) {
        if (HttpSessionUtils.isLogin(session)) {
            return "redirect:/";
        }
        Optional<User> maybeUser = userRepo.findByUserId(userId);
        if (!maybeUser.isPresent() || !maybeUser.filter(userInfo -> userInfo.isMatch(passwd)).isPresent()) {
            return "redirect:/users/loginFail";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, maybeUser.get());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userRepo.findById(id).get());
        return "/users/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        HttpSessionUtils.logout(session);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session, id).get();
        model.addAttribute("user", sessionUser);
        return "/users/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String currentPasswd, User updateInfo, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session).get();
        User user = userRepo.findById(id).get();
        user.update(sessionUser, currentPasswd, updateInfo);
        userRepo.save(user);
        return "redirect:/users/" + id;
    }
}
