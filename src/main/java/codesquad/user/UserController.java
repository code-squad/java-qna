package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/users/form")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = findUser(userId);

        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = findUser(userId);
        model.addAttribute("user", user);

        users.remove(user);
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(User user) {
        users.add(user);

        return "redirect:/users";
    }

    public User findUser(String userId) {
        return users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(User.defaultUser);
    }
}
