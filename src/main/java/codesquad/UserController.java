package codesquad;

import codesquad.model.User;
import codesquad.model.UserRepository;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findUserByUserId(userId);
        if (!maybeUser.isPresent()) {
            logger.debug("User with userId: {} is not present in User DB", userId);
            return "redirect:/users/login";
        }

        try {
            User user = maybeUser.filter(u -> u.passwordMatch(password))
                    .orElseThrow(IllegalArgumentException::new);
            session.setAttribute("user", user);
            logger.debug("User login session saved for User: {}", user);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            logger.info("비밀번호 불일치");
            return "redirect:/users/login";
        }
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        logger.debug("User: {}", user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        User user = userRepository.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {
        User user = userRepository.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, User newUser, String oldPassword) {
        User user = userRepository.getUserByUserId(userId);
        user.updateUserInfo(newUser, oldPassword);
        userRepository.save(user);
        logger.info("User information update complete for User: {}", user);
        return "redirect:/users/list";
    }
}
