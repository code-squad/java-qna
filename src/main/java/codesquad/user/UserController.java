package codesquad.user;

import codesquad.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(User user) {
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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model,  HttpSession session) {
        checkLoginUser(session);
        checkUserSelf(id, session);

        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        checkLoginUser(session);
        User loginUser = checkUserSelf(id, session);

        loginUser.update(updatedUser);
        userRepository.save(loginUser);
        return "redirect:/users";
    }

    private User checkUserSelf(@PathVariable Long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!loginUser.matchId(id)) {
            throw new IllegalStateException("You can't edit the other user's info");
        }
        return loginUser;
    }

    private String checkLoginUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) return "redirect:/user/login";
        return null;
    }
}
