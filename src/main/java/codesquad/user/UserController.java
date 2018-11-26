package codesquad.user;

import codesquad.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static codesquad.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(User user, Model model) {
        log.debug("create : {}", user);
        userRepository.save(user);
        return "redirect:/users/";
    }

    @GetMapping
    public String list(Model model) {
        log.debug("view user list");

        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/form")
    public String join() {
        log.debug("view user sign up form");

        return "/user/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id,Model model) {
        log.debug("view user profile");

        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        log.debug("view user update form");

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        if(!loggedInUser.isMatchId(id)) {
            return "/error/access";
        }

        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "/user/update_form";
    }

    @PutMapping("/{id}")
    public String update(User updatedUser, HttpSession session) {
        log.debug("update user");

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);

        if(!loggedInUser.isMatchPassword(updatedUser.getPassword())) {
            return "user/update_failed";
        }

        loggedInUser.update(updatedUser);
        userRepository.save(loggedInUser);
        return "redirect:/users/";
    }

    @GetMapping("/login")
    public String loginForm() {
        log.debug("view user login form");

        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        log.debug("user login");

        Optional<User> maybeUser = userRepository.findByUserId(userId);

        if(!maybeUser.isPresent()) {
            log.debug("아이디 불일치");
            return "/user/login_failed";
        }

        if(!maybeUser.get().isMatchPassword(password)) {
            log.debug("비밀번호 불일치");
            return "/user/login_failed";
        }

        session.setAttribute(USER_SESSION_KEY, maybeUser.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.debug("user logout");

        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }
}

//    @GetMapping("/{id}")
//    public ModelAndView show(@PathVariable long id) {
//        ModelAndView mav = new ModelAndView("user/profile");
//        mav.addObject("user", userRepository.findById(id).orElse(null));
//        return mav;
//    }
