package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    UserRepositoryImpl userRepositoryImpl = UserRepositoryImpl.INSTANCE;

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("/signUp")
    public String create(User user) {
        userRepositoryImpl.addUser(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepositoryImpl);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepositoryImpl.findUser(userId));
        return "user/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepositoryImpl.findUser(userId));
        return "user/updateForm";
    }

    @PostMapping("/{userId}")
    public String update(User user) {
        userRepositoryImpl.modifyUser(user);
        return "redirect:/users";
    }

}
