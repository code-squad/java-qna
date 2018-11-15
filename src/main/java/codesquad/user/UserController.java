package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("/signUp")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        return "user/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }

}
