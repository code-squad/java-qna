package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.exceptions.PasswordMismatchException;
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
            User user = maybeUser
                    .filter(u -> u.passwordsMatch(password))
                    .orElseThrow(PasswordMismatchException::new);
            session.setAttribute(HTTP_SESSION_KEY, user);
            logger.debug("User login for User: {}", user);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            logger.debug("User.password.mismatch");
            return "redirect:/users/loginFailed";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        try {
            endSession(session);
            logger.debug("User Logout for User: {}", getUserFromSession(session));
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
        }
        return "/";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        logger.info("New User created successfully: {}", user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute(USER_LIST, userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{userId}/profile")
    public String getProfile(@PathVariable String userId, Model model) {
        User user = userRepository.getUserByUserId(userId);
        model.addAttribute(USER, user);
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String getUpdateForm(Model model, HttpSession session) {
        try {
            User user = getUserFromSession(session);
            model.addAttribute(USER, user);
            return "/users/updateForm";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        }
    }

    @PutMapping("/{userId}/update")
    public String updateUser(HttpSession session, User newUser, String oldPassword) {
        try {
            User user = getUserFromSession(session);
            user.updateUserInfo(newUser, oldPassword);
            userRepository.save(user);
            logger.info("User information update complete for User: {}", user);
            return "redirect:/";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        } catch (PasswordMismatchException e) {
            logger.debug(e.getMessage());
            return "redirect:/";
        }
    }
}