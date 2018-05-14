package codesquad;

import codesquad.model.User;
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
    List<User> users = new ArrayList<>();

    @PostMapping("/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isMatch(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isMatch(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "/users/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(String userId, String oldPassword, String newPassword, String name, String email) {
        try {
            for (User u : users) {
                if (u.isMatch(userId)) {
                    u.updateUserInfo(oldPassword, newPassword, name, email);
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/users/" + userId + "/form";
        }
        return "redirect:/users/list";
    }
}
