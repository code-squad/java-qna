package codesquad;

import codesquad.model.User;
import codesquad.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = userRepository.findUserByUserId(userId);
        model.addAttribute("user", user);
        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = userRepository.findUserByUserId(userId);
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, User newUser, String oldPassword) {
        try {
            User user = userRepository.findUserByUserId(userId);
            user.updateUserInfo(newUser, oldPassword);
            userRepository.save(user);
            return "redirect:/users/list";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/users/list";
        }
    }
}
