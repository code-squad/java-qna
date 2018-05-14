package codesquad;

import codesquad.model.User;
import codesquad.model.Users;
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
    private Users users = new Users();

    @PostMapping("/create")
    public String create(User user) {
        users.addUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        try {
            User user = users.getUser(userId);
            model.addAttribute("user", user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        try {
            User user = users.getUser(userId);
            model.addAttribute("user", user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return "/users/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(String userId, String oldPassword, String newPassword, String name, String email) {
        try {
            users.updateUser(userId, oldPassword, newPassword, name, email);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/users/" + userId + "/form";
        }
        return "redirect:/users/list";
    }
}
