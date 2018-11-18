package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private Users users = Users.of();

    @PostMapping
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", users.getUsers());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        model.addAttribute("user", matchUser(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        model.addAttribute("user", matchUser(userId));
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/date")
    public String update(User putValue, @PathVariable String userId) {
        users.update(putValue, matchUser(userId));
        return "redirect:/users";
    }

    private User matchUser(String userId) {
        for (User user : users.getUsers()) if (user.isSameUser(userId)) return user;
        return null;
    }
}
