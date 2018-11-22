package codesquad.base.user;

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
    private userRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);

        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute("sessionedUser", user);
                System.out.println("성공!!");
                return "redirect:/";
            }
        }
        return "redirect:/users/loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("sessionedUser");
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
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {

        if (userRepository.findById(id).orElseGet(null) == null) {
            // todo 예외처리
            return "/user/login";
        }
        model.addAttribute("user", userRepository.findById(id).orElseGet(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable Long id) {
        User user = userRepository.findById(id).orElseGet(null);

        model.addAttribute("users", user);

        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(Model model, @PathVariable Long id, User updateUser) {
        User user = userRepository.findById(id).orElseGet(null);
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}