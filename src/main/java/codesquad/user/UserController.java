package codesquad.user;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable("id") Long id) {
        model.addAttribute("users", userRepository.findById(id).orElseGet(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("users", userRepository.findById(id).orElseGet(null));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(User newUser, @PathVariable Long id) {

        User user = userRepository.findById(id).orElseGet(null);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}