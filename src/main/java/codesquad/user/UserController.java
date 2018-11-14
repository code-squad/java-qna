package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @PostMapping("/create")
    public String create(User user) {
        UserRepository.getInstance().addUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", UserRepository.getInstance().getUsers());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute(
                UserRepository.getInstance().getUsers()
                        .stream().filter(x -> x.getUserId().equals(userId)).findFirst().orElse(null));
        return "user/profile";
    }
}
