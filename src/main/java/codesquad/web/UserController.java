package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping
    public String join(User user) {
        user.setIndex(users.size() + 1);
        this.users.add(user);

        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User foundUser = findUser(userId);

        model.addAttribute("user", foundUser);

        return "user/profile";
    }

    private User findUser(@PathVariable String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }

        return null;
    }
}
