package codesquad.user;

import codesquad.HttpSessionUtil;
import codesquad.Result;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String create(User user) {
        if (logger.isDebugEnabled()) {
            logger.debug("User : " + user);
        }

        logger.debug("User : {}", user );

        logger.warn("test1");
        logger.info("test2");
        logger.error("test");
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/profile")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return "/user/profile";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, User updateUser, HttpSession session) {
       if (!HttpSessionUtil.isLoginUser(session)) {
           return "/user/login_failed";
       }

       User loginUser = userRepository.findById(id).orElseThrow(NullPointerException::new);

        User user = userRepository.findById(id)
                .filter(user1 -> user1.update(updateUser, loginUser))
                .orElseThrow(NullPointerException::new);
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(HttpSession session, String userId, String password) {
        User user = checkLoginUser(userId, password);
        session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    private User checkLoginUser(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtil.USER_SESSION_KEY);
        return "redirect:/";
    }
}
