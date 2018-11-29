package codesquad.user;

import codesquad.exception.Result;
import codesquad.util.SessionUtil;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
    public String login(String userId, String password, Model model, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (isLoginValid(model, password, maybeUser)) return "/user/login";

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
        if (isPermissionValid(model, session, id)) return "/user/login";

        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User updatedUser, Model model, HttpSession session) {
        if (isPermissionValid(model, session, id)) return "/user/login";
        User sessionedUser = SessionUtil.getUserFromSession(session);

        sessionedUser.update(updatedUser);
        userRepository.save(sessionedUser);
        return "redirect:/users";
    }

    private boolean isPermissionValid(Model model, HttpSession session, long id) {
        Result result = permissionValid(session, id);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result permissionValid(HttpSession session, long id) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }
        User sessionedUser = SessionUtil.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            return Result.fail("You can't edit the other user's info");
        }

        return Result.success();
    }

    private boolean isLoginValid(Model model, String password, Optional<User> maybeUser) {
        Result result = loginValid(password, maybeUser);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result loginValid(String password, Optional<User> maybeUser) {
        if (!maybeUser.isPresent()) {
            return Result.fail("Id doesn't exist");
        }
        if (!maybeUser.get().matchPassword(password)) {
            return Result.fail("Wrong password");
        }

        return Result.success();
    }
}
