package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = UserRepository.getInstance();

    @PostMapping
    public String create(User user) {
        user.setIndex(users.size() + 1);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        User user = users.stream().filter(users -> users.isUserId(userId)).findAny().get();
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUserInfo(@PathVariable String userId, Model model) {
        User user = users.stream().filter(users -> users.isUserId(userId)).findAny().get();
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/{userId}")
    public String updateUserInfo(@PathVariable String userId, User userUpdated) {
        users = users.stream()
                .map(theUser -> theUser.isUserId(userId)? userUpdated : theUser)
                .collect(Collectors.toList());
        return "redirect:/users";
    }

}
