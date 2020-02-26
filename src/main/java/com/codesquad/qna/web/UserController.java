package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    private final String loginSessionName = "sessionedUser";

    @PostMapping("")
    public String create(User newUser) {
        userRepository.save(newUser);
        log.info("signUp : {}", newUser);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", findUser(userId, true));
        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model, HttpSession session) {
        Object tempUser = session.getAttribute(loginSessionName);
        log.info("current Session User Data : {}", tempUser);
        if (tempUser == null)
            return "redirect:/user/login";

        User sessionedUser = (User)tempUser;
        if (!userId.equals(sessionedUser.getUserId()))
            throw new IllegalStateException("You can only update your own");

        model.addAttribute("user", findUser(userId, true));
        return "users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable String userId, User updateUser, HttpSession session) {
        Object tempUser = session.getAttribute(loginSessionName);
        log.info("current Session User Data : {}", tempUser);
        if (tempUser == null)
            return "redirect:/user/login";

        User sessionedUser = (User)tempUser;
        if (!userId.equals(sessionedUser.getUserId()))
            throw new IllegalStateException("You can only update your own");

        User originUser = findUser(userId, true);
        if (!originUser.matchPassword(updateUser))
            return "users/update_failed";

        originUser.update(updateUser);
        userRepository.save(originUser);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(User sessionUser, HttpSession session) {
        User user = findUser(sessionUser.getUserId(), false);
        String loginFailedTemplate = "/users/login_failed";
        if (user == null)
            return loginFailedTemplate;

        if (!user.matchPassword(sessionUser))
            return loginFailedTemplate;

        session.setAttribute(loginSessionName, user);
        log.info("login Success : {}" , user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(loginSessionName);
        log.info("logout Success");
        return "redirect:/";
    }

    private User findUser(String userId, boolean isThrowable) {
        log.info("findUser: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (isThrowable)
            return user.orElseThrow(IllegalArgumentException::new);
        return user.orElse(null);
    }
}
