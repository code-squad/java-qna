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

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (HttpSessionUtils.isLogin(session)) {
            return "redirect:/";
        }
        return "/users/login";
    }

    @PostMapping("/login")
    public String logining(String userId, String passwd, HttpSession session) {
        if (HttpSessionUtils.isLogin(session)) {
            throw new IllegalArgumentException("You are already login");
        }

        Optional<User> maybeUser = userRepo.findByUserId(userId);
        if (!maybeUser.filter(userInfo -> userInfo.isMatch(passwd)).isPresent()) {
            return "redirect:/users/loginFail";
        }
        session.setAttribute("sessionedUser", maybeUser.get());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userRepo.findById(id).get());
        return "/users/profile";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
        validateRequest(session, id);

        model.addAttribute("user", userRepo.findById(id).get());
        return "/users/edit";
    }


    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String currentPasswd, User updateInfo, HttpSession session) {
        validateRequest(session, id);

        User user = userRepo.findById(id).get();
        user.changeInfo(currentPasswd, updateInfo);
        userRepo.save(user);
        return "redirect:/users/" + id;
    }

    private void validateRequest(HttpSession session, Long pathId) throws IllegalArgumentException {
        if (!HttpSessionUtils.isLogin(session)) {
            throw new IllegalArgumentException("required Login");
        }

        if (!HttpSessionUtils.getUserFromSession(session, pathId).isPresent()) {
            throw new IllegalArgumentException("request to change another user's information.");
        }
    }
}
