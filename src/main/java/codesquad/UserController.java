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

    @PostMapping("/user/create/")
    public String create(User user) {
        users.add(user);
        System.out.println(user);
        return "redirect:/users";
    }

    @GetMapping("users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(Model model, @PathVariable("userId") String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute(user);
            }
        }
        return "/user/profile";
    }
}