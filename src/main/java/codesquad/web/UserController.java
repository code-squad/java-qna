package codesquad.web;

import codesquad.domain.User;
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

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @PostMapping()
    public String create(User user) {
        users.add(user);
        System.out.println("user is " + user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = findUserId(userId);
        System.out.println("users size is " + users.size());
        if (user == null) {
            return "error";
        }
        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/{userId}/form")
    public String getUpdateForm(@PathVariable String userId, Model model) {
        User user = findUserId(userId);
        if (user == null) {
            return "error";
        }
        model.addAttribute("user", user);

        return "user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUserData(@PathVariable String userId, String password, String email) {
        User user = findUserId(userId);
        user.setPassword(password);
        user.setEmail(email);

        return "redirect:/users";
    }

    private User findUserId(String userId) {
        for (User user : users) {
            if (user.isValidUserId(userId)) {
                return user;
            }
        }
        return null;
    }
}
