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

    @PostMapping("/create")
    public String create(User user){
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model){
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model){
        setUpUser(userId, model);
        return "user/profile";
    }

    private Model setUpUser(String userId, Model model){
        User user = findUserById(userId);
        model.addAttribute("user", user);
        return model;
    }

    private User findUserById(String userId){
        for (User user : users) {
            if(user.isUser(userId)) return user;
        }
        return null;
    }

    @GetMapping("/{userId}/form")
    public String showModifyView(@PathVariable String userId, Model model){
        setUpUser(userId, model);
        return "user/updateForm";
    }

    private int findIndexById(String userId){
        return users.indexOf(findUserById(userId));
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, User user){
        users.set(findIndexById(userId), user);
        return "redirect:/users";
    }
}
