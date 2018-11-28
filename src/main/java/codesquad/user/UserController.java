package codesquad.user;

import codesquad.exception.Result;
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

//        userRepository.findByUserId(userId)
//                .filter(user -> user.matchPassword(password))
//                .map(user -> SessionUtil.setUserToSession(session, user))
//                .orElseThrow(IllegalAccessError::new)

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
        if (isValid(model, session, id)) return "/user/login";

        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User updatedUser, Model model, HttpSession session) {
        if (isValid(model, session, id)) return "/user/login";
        User sessionedUser = SessionUtil.getUserFromSession(session);

        sessionedUser.update(updatedUser);
        userRepository.save(sessionedUser);
        return "redirect:/users";
    }


    private boolean isValid(Model model, HttpSession session, long id) {
        Result result = valid(session, id);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result valid(HttpSession session, long id) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }
        User sessionedUser = SessionUtil.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            return Result.fail("You can't edit the other user's info");
        }

        return Result.success();
    }
}
