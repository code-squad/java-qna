package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String readProfiles(Model model, @PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", user.get());
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "user/update_form";
    }

    @PutMapping("/{userId}")
    public String update(@PathVariable String userId, User updateUserInfo) {
        updateUserInfo.setUserId(userId);
        Optional<User> user = userRepository.findById(userId);
        if (updateUserInfo.checkPassword(user)) {
            userRepository.save(updateUserInfo);
        }
        return "redirect:/users";
    }
}
