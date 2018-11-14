package codesquad.user;

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

    @PostMapping("")
    public String create(User user) {
        System.out.println("excute create!");
        System.out.println("user : " + user);
        users.add(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null));
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null));
        return "user/updateForm";
    }

    @PostMapping("/{userId}")
    public String modifyForm(User modifyUser) {
        for (User user : users) {
            user.update(modifyUser);
        }
        return "redirect:/users";
    }
}
