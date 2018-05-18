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

import static codesquad.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USER_LIST = "users";
    private static final String USER = "user";
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
            User user = maybeUser.filter(u -> u.passwordsMatch(password))
                    .orElseThrow(IllegalArgumentException::new);
            session.setAttribute(HTTP_SESSION_KEY, user);
            logger.debug("User login for User: {}", user);
            return "redirect:/";

        } catch (IllegalArgumentException e) {
            logger.debug("User login FAILED: incorrect password.");
            return "redirect:/users/loginFailed";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        logger.debug("Logout User: {}", getUserFromSession(session));
        session.removeAttribute(HTTP_SESSION_KEY);
        logger.debug("User Logout for User: {}", getUserFromSession(session));
        return "/";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        logger.trace("New User created successfully: {}", user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute(USER_LIST, userRepository.findAll());
        logger.trace("Users list added to model. Redirecting to user list...");
        return "/users/list";
    }

    @GetMapping("/{userId}/profile")
    public String getProfile(@PathVariable String userId, Model model) {
        User user = userRepository.getUserByUserId(userId);
        model.addAttribute(USER, user);
        logger.trace("User added to Model. Redirecting to user profile...");
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(Model model, HttpSession session) {
        if (!userIsLoggedIn(session)) {
            logger.debug("User is NOT logged in.");
            return "redirect:/users/loginForm";
        }

        User user = getUserFromSession(session);
        model.addAttribute(USER, user);
        logger.trace("User added to Model. Directing to update form...");
        return "/users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(HttpSession session, User newUser, String oldPassword) {
        if (!userIsLoggedIn(session)) {
            logger.debug("User is NOT logged in.");
            return "redirect:/users/loginForm";
        }

        try {
            User user = getUserFromSession(session);
            user.updateUserInfo(newUser, oldPassword);
            userRepository.save(user);
            logger.info("User information update complete for User: {}", user);
            return "redirect:/";

        } catch (IllegalArgumentException e) {
            logger.debug(e.getMessage());
            //TODO: 예외처리는 어떻게 해야하나? 어디로 사용자를 redirect해줘야 하나?
            return "redirect:/";
        }
    }
}
