package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User sessionUser = userRepository.findByUserId(userId);

        if (sessionUser == null) {
            log.debug("Login Failed");
            return "redirect:/user/login.html";
        }

        if (!sessionUser.matchPassword(password)) {
            log.debug("Login Failed");
            return "redirect:/user/login.html";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, sessionUser);
        log.debug("Login Success");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.debug("Logout");
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping
    public String create(User user) {
        log.debug("User : {}", user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String view(Model model) {
        log.debug("User Size : {}", userRepository.count());
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findOne(id);
        log.debug("User : {}", user);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String searchUser(@PathVariable Long id, Model model, HttpSession session) {
        try {
            User sessionedUser = hasPermission(session, id);
            model.addAttribute("user", sessionedUser);
            return "user/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, Model model, User updateUser, String checkPassword, HttpSession session) {
        try {
            log.debug("checkPassword : {}", checkPassword);
            User sessionedUser = hasPermission(session, id);
            sessionedUser.updateUser(updateUser, checkPassword);
            userRepository.save(sessionedUser);
            return "redirect:/users";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private User hasPermission(HttpSession session, Long id) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("You need login");
        }
        User sessionedUser = HttpSessionUtils.getSessionedUser(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException("You can't update another user.");
        }
        return sessionedUser;
    }
}
