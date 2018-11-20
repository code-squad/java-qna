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
        Optional<User> maybeuser = userRepository.findByUserId(userId);

        if (maybeuser.isPresent()) {
            User user = maybeuser.get();
            if (user.matchPassword(password)) {
                session.setAttribute(SessionUtil.USER_SESSION_KEY, user);
                return "redirect:/";
            }
        }
        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtil.USER_SESSION_KEY);
        return "redirect:/";
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!SessionUtil.permissionCheck(session, id)) return "redirect:/users/login";

        model.addAttribute("user", userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updateUser, HttpSession session) {
        if (!SessionUtil.permissionCheck(session, id)) return "redirect:/users/login";

        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }


}
