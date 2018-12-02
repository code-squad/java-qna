package codesquad.user;

import codesquad.aspect.SessionCheck;
import codesquad.config.HttpSessionUtils;
import codesquad.utils.Result;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String createUser(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable long id, Model model) throws UserNotFoundException {
        User user = getUser(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showUserInfo(@SessionCheck HttpSession session, Model model, @PathVariable long id) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Result result = sessionedUser.isSameUser(id);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        model.addAttribute("user", getUser(id));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUserInfo(@SessionCheck HttpSession session, Model model, @PathVariable long id, User updatedUser) {
        User user = getUser(id);
        Result result = user.update(updatedUser, HttpSessionUtils.getUserFromSession(session));
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        userRepository.save(user);
        return "redirect:/users";
    }


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
                return "redirect:/";
            }
        }
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    private User getUser(@PathVariable long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));
    }

}
