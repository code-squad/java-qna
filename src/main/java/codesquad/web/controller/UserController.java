package codesquad.web.controller;

import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static codesquad.web.utils.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.web.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    // post는 받아서 전달
    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        log.info("Create user : {} ", user.toString());
        return "redirect:/users"; // 얘는 get맵핑이 되어 있어야 함.
    }

    // get은 가진 것을 뿌려줌
    @GetMapping("")
    public String list(Model model) {
        log.debug("show list");
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String showUserByUserId(@PathVariable("userId") String userId) {
        log.debug("user id : {}", userId);
        return "redirect:/users/" + userRepository.findByUserId(userId)
                .map(u -> String.valueOf(u.getId()))
                .orElse("");
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showUpdatePage(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
        if (!id.equals(sessionUser.getId())) {
            throw new IllegalStateException("Cannot update other's profile");
        }
        model.addAttribute("user", userRepository.findById(sessionUser.getId())
                .orElseThrow(RuntimeException::new));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String beforePassword, User updateUser, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
        if (!id.equals(sessionUser.getId())) {
            throw new IllegalStateException("Cannot update other's profile");
        }
        userRepository.findById(sessionUser.getId())
                .filter(u -> u.update(beforePassword, updateUser))
                .ifPresent(u -> userRepository.save(u));
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        log.debug("login - id : {}, pw : {}", userId, password);
        return userRepository.findByUserId(userId)
                .filter(u -> u.isMatch(password))
                .map(u -> {
                    session.setAttribute(USER_SESSION_KEY, u);
                    return "redirect:/";
                })
                .orElse("redirect:/users/loginForm");
    }

}
