package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }

    @PostMapping()
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {

        return "profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));

        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUserData(@PathVariable Long id, User newUser) {
        User user = userRepository.findOne(id);
        user.update(newUser);
        userRepository.save(user);

        return "redirect:/users";
    }
}
