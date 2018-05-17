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

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findUserByUserId(userId);
        if (!maybeUser.isPresent()) {
            logger.debug("User with userId: {} is not present in User DB.", userId);
            return "redirect:/users/loginFailed";
        }

        try {
            User user = maybeUser.filter(u -> u.passwordMatch(password))
                    .orElseThrow(IllegalArgumentException::new);
            session.setAttribute("loggedInUser", user);
            logger.debug("User login SUCCESSFUL for User: {}", user);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            logger.debug("User login FAILED: incorrect password.");
            return "redirect:/users/loginFailed";
        }
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        logger.trace("New User created successfully: {}", user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        logger.trace("Users list added to model. Redirecting to user list...");
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        User user = userRepository.getUserByUserId(userId);
        model.addAttribute("user", user);
        logger.trace("User added to Model. Redirecting to user profile...");
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            logger.debug("User is NOT logged in.");
            return "redirect:/users/loginForm";
        }

        model.addAttribute("user", user);
        logger.trace("User added to Model. Redirecting to update form...");
        return "/users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(HttpSession session, User newUser, String oldPassword) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            logger.debug("User is NOT logged in.");
            return "redirect:/users/loginForm";
        }

        user.updateUserInfo(newUser, oldPassword);
        userRepository.save(user);
        logger.info("User information update complete for User: {}", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        logger.debug("Logout User: {}", session.getAttribute("loggedInUser"));
        session.removeAttribute("loggedInUser");
        logger.debug("User Logout SUCCESSFUL. User: {}", session.getAttribute("loggedInUser"));
        return "/";
    }
}
