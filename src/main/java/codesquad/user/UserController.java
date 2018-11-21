package codesquad.user;

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

    @PostMapping("")
    public String create(User user) {
        System.out.println("excute create!");
        System.out.println("user : " + user);
        System.out.println(user.getId());
        userRepository.save(user);

        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginUser = (User)session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if(loginUser.matchId(id)) {
            model.addAttribute("user", userRepository.findById(id).orElse(null));
            return "user/updateForm";
        }
        return "user/matchUserId_failed";
    }

    @PutMapping("/{id}")
    public String modifyForm(User modifyUser, HttpSession session) {
        User loginUser = (User)session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if(loginUser.update(modifyUser)) {
            userRepository.save(loginUser);
            return "redirect:/users";
        }
        return "user/update_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        User user = userRepository.findByUserId(userId)
                .filter(u -> u.matchPassword(password))
                .orElse(null);

        return addSessionUser(user, session);
    }

    public String addSessionUser(User user, HttpSession session) {
        if (user != null) {
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        }
        return "user/login_failed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
