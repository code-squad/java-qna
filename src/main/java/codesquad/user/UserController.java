package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
    private Users users = new Users();

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users.getAll());
        return "user/list";
    }

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}")
    public String readProfiles(Model model, @PathVariable String userId) {
        Optional<User> user = users.findUser(userId);
        if (!user.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", user.get());
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        Optional<User> user = users.findUser(userId);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "user/update_form";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, User updateUserInfo) {
        updateUserInfo.setUserId(userId);
        users.update(updateUserInfo);
        return "redirect:/users";
    }

    @PostMapping("/test")
    public String test() {
        System.out.println("execute create!!");
        return "index";
    }
}
