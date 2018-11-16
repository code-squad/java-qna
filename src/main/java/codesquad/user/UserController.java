package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList();

    @PostMapping("/users/create")
    public String create(User user){
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model){
        setUpUser(userId, model);
        return "user/profile";
    }

    public Model setUpUser(String userId, Model model){
        User user = findUserById(userId);
        model.addAttribute("user", user);
        return model;
    }

    public User findUserById(String userId){
        for (User user : users) {
            if(user.isUser(userId)) return user;
        }
        return null;
    }

    @GetMapping("/users/{userId}/form")
    public String modify(@PathVariable String userId, Model model){
        setUpUser(userId, model);
        System.out.println("?");
        return "user/updateForm";
    }
}
