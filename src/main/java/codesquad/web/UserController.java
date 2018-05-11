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

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);
        System.out.println("user is " + user);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = findUserId(userId);
        System.out.println("users size is " + users.size());
        if (user == null) {
            return "error";
        }
        model.addAttribute("user", user);

        return "profile";
    }

    public User findUserId(String userId) {
        for (User user : users) {
            if (user.isValidUserId(userId)) {
                return user;
            }
        }
        return null;
    }
}
