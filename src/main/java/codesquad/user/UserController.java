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
    public String profile(Model model, @PathVariable String userId) {
        User user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        User user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/update")
    public String updateForm(User newUser) {
        for (User user : users) {
            if (newUser.getUserId().equals(user.getUserId())) {
                user.setName(newUser.getName());
                user.setEmail(newUser.getEmail());
                user.setPassword(newUser.getPassword());
            }
        }
        return "redirect:/users";
    }

}
