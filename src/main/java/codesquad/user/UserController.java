package codesquad.user;

import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("/signUp")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        return "user/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }


    @PostMapping("/signIn")
    public String signIn(String userId, String password, HttpSession session) {
        if (isSignInSuccess(userId, password, session))
            return "redirect:/";
        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtil.USER_SESSION_KEY);
        return "redirect:/";
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (!SessionUtil.permissionCheck(session, user)) return "redirect:/users/login";

        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updateUser, HttpSession session) {
        if (!SessionUtil.isLoginUser(session)) return "redirect:/users/login";

        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        user.update(updateUser, id);
        userRepository.save(user);
        return "redirect:/users";
    }

    private boolean isSignInSuccess(String userId, String password, HttpSession session) {
        try {
            userRepository.findByUserId(userId)
                    .filter(user -> user.matchPassword(password))
                    .map(user -> SessionUtil.setUserToSession(session, user))
                    .orElseThrow(IllegalAccessError::new);
        } catch (IllegalAccessError e) {
            return false;
        }
        return true;
    }

}
