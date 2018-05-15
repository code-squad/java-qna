package codesquad.web;

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

    @PostMapping("users")
    public String join(User user) {
        this.users.add(user);

        return "redirect:/users";
    }

    @GetMapping("users")
    public String list(Model model) {
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User foundUser = null;
        foundUser = findUser(userId, foundUser);

        model.addAttribute("user", foundUser);

        return "user/profile";
    }

    private User findUser(@PathVariable String userId, User foundUser) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                foundUser = user;
                break;
            }
        }
        return foundUser;
    }
}
