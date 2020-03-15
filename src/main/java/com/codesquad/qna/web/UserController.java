package com.codesquad.qna.web;

import com.codesquad.qna.global.error.exception.DataNotFoundException;
import com.codesquad.qna.global.error.exception.ErrorCode;
import com.codesquad.qna.global.error.exception.RequestNotAllowedException;
import com.codesquad.qna.model.User;
import com.codesquad.qna.model.UserRepository;
import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User newUser) {
        if (Objects.nonNull(findUser(newUser.getUserId(), true)))
            return "users/join_failed";

        userRepository.save(newUser);
        log.debug("signUp : {}", newUser);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{userId}")
    public String show(@PathVariable String userId, Model model) {
        model.addAttribute("user", findUser(userId, false));
        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model, @RequestAttribute User sessionedUser) {
        User user = getMatchedUser(userId, sessionedUser);
        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable String userId, User updateUser, @RequestAttribute User sessionedUser) {
        User originUser = getMatchedUser(userId, sessionedUser);
        if (!originUser.matchPassword(updateUser))
            return "users/update_failed";

        originUser.update(updateUser);
        userRepository.save(originUser);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(User loginUser, HttpSession session) {
        String loginFailedTemplate = "/users/login_failed";
        User user = findUser(loginUser.getUserId(), true);
        if (user == null)
            return loginFailedTemplate;

        if (!user.matchPassword(loginUser))
            return loginFailedTemplate;

        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        log.debug("login Success : {}" , user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        log.debug("logout Success");
        return "redirect:/";
    }

    private User findUser(String userId, boolean isNullable) {
        log.debug("findUser: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        return isNullable ? user.orElse(null) : user.orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }

    private User getMatchedUser(String userId, User sessionedUser) {
        if (!sessionedUser.matchId(userId))
            throw new RequestNotAllowedException(ErrorCode.FORBIDDEN);

        return findUser(userId, false);
    }
}
