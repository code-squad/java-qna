package codesquad.user;

import codesquad.aspect.LoginCheck;
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
    UserRepository userRepository;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @PostMapping
    public String signUp(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    @LoginCheck
    public String read(HttpSession session, Model model, @PathVariable String userId) {
        User user = (User) session.getAttribute(User.SESSION_NAME);
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent() && user.equals(maybeUser.get())) {
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "redirect:/";
    }

    @GetMapping("/{userId}/form")
    @LoginCheck
    public String updateForm(HttpSession session, Model model, @PathVariable String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/update_form";
        }
        return "redirect:/users";
    }

    @PutMapping("/{userId}")
    @LoginCheck
    public String update(HttpSession session, @PathVariable String userId, User updateUserInfo) {
        User user = (User) session.getAttribute(User.SESSION_NAME);
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent() && user.equals(maybeUser.get()) && updateUserInfo.checkPassword(maybeUser.get())) {
            user.updateNameEmail(updateUserInfo);
            userRepository.save(user);
            return "redirect:/users";
        }
        return "redirect:/error";
    }

    @GetMapping("login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent() && maybeUser.get().checkPassword(password)) {
            session.setAttribute(User.SESSION_NAME, maybeUser.get());
            return "redirect:/";
        }
        return "redirect:/error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(User.SESSION_NAME);
        return "redirect:/";
    }
}
