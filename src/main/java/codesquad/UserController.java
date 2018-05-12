package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isMatch(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "/user/profile";
    }

    @GetMapping("/user/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/user/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isMatch(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "/user/updateForm";
    }

    @PostMapping("/user/{userId}/update")
    public String updateUser(String userId, String oldPassword, String newPassword, String name, String email) {
        System.out.println(userId);
        System.out.println(oldPassword);
        System.out.println(newPassword);
        System.out.println(name);
        try {
            for (User u : users) {
                if (u.isMatch(userId)) {
                    u.updateUserInfo(oldPassword, newPassword, name, email);
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            return "redirect:/user/" + userId + "/form";
        }
        return "redirect:/user/list";
    }
}
