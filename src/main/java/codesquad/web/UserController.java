package codesquad.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
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

    @PostMapping("/users")
    public String create(User user) {
        System.out.println(user);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String view(Model model) {
        System.out.println(users.size());
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.match(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String searchUser(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.match(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String updateUser(@PathVariable String userId, User updateUser, String checkPassword) {
        System.out.println("checkPassword : " + checkPassword);
        for (User user : users) {
            if (user.match(userId)) {
                user.updateUser(updateUser, checkPassword);
                break;
            }
        }
        return "redirect:/users";
    }
}
