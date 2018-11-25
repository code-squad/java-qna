package codesquad.user;

import codesquad.util.SessionUtil;
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

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "user/form";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent() || !maybeUser.get().matchPassword(password)) return "/user/login_failed";
        session.setAttribute(SessionUtil.USER_SESSION_KEY, maybeUser.get());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtil.USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        checkSessionedUser(session);
        checkUserSelf(id, session);

        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User updatedUser, HttpSession session) {
        checkSessionedUser(session);
        User sessionedUser = checkUserSelf(id, session);

        sessionedUser.update(updatedUser);
        userRepository.save(sessionedUser);
        return "redirect:/users";
    }

    private String checkSessionedUser(HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) return "redirect:/users/login";
        return null;
    }

    private User checkUserSelf(@PathVariable long id, HttpSession session) {
        User sessionedUser = (User)session.getAttribute(SessionUtil.USER_SESSION_KEY);
        if (!sessionedUser.matchId(id)) throw new IllegalStateException("You can't edit the other user's info");
        return sessionedUser;
    }
}
