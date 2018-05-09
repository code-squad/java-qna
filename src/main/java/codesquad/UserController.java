package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/create")
    public String create(User user, Model model){
        users.add(user);
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "hello";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("users", users);
        return "list";
    }
}
