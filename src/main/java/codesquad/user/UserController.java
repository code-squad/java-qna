package codesquad.user;

import codesquad.config.HttpSessionUtils;
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
    public String showUserInfo(@PathVariable long id, Model model, HttpSession session) {
        String validResult = sessionValidCheck(id, session);
        if (validResult != null) return validResult;

        model.addAttribute("user", getUser(id));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUserInfo(@PathVariable long id, User updatedUser, HttpSession session) throws UserNotFoundException {
        String validResult = sessionValidCheck(id, session);
        if (validResult != null) return validResult;

        User user = getUser(id);
        user.update(updatedUser);
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

    private String sessionValidCheck(@PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "redirect:/user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            return "common/wrongApproach";
        }
        return null;
    }

    private User getUser(@PathVariable long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));
    }

}
