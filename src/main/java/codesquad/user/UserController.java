package codesquad.user;

import codesquad.HttpSessionUtil;
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

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String create(User user) {
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
            return "redirect:/user/login";
        }

        User sessionedUser = HttpSessionUtil.getUserFromSession(session);
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("You can't update the another user");
        }

        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        if (user.matchPassword(updateUser.getPassword())) {
            user.update(updateUser);
            userRepository.save(user);
            return "redirect:/users";
        }
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(HttpSession session, String userId, String password) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user);
                return "redirect:/";
            }
        }
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtil.USER_SESSION_KEY);
        return "redirect:/";
    }
}
