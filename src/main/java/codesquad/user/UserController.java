package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private List<User> users = TestUsers.addUsers();

    @PostMapping("/users")
    public String create(User user) {  // 매번 요청될 때마다 SpringFramework에서 만들어줘
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        User user = users.stream().filter(users -> users.isUserId(userId)).findAny().get();
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String showUserInfo(@PathVariable String userId, Model model) {
        User user = users.stream().filter(users -> users.isUserId(userId)).findAny().get();
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}")
    public String updateUserInfo(@PathVariable String userId, User userUpdated) {
        users = users.stream()
                .map(theUser -> theUser.isUserId(userId)? userUpdated : theUser)
                .collect(Collectors.toList());
        return "redirect:/users";
    }

}
