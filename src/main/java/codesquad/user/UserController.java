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

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) { //인자로 User user를 받아도 되고, 인자가 얼마안되니 저렇게 해도됨.
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute("loginUser", user);
            }
        }
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable Long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable Long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(User modifiedUser, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        loginUser.update(modifiedUser);
        userRepository.save(loginUser);
        return "redirect:/users";
    }
}
