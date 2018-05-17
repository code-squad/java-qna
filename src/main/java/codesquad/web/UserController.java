package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String join(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userRepository.getOne(userId));

        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        User user = userRepository.findOne(id);
        user.update(newUser);
        // 없으면 insert, 기존 사용자면 update
        userRepository.save(user);
        return "redirect:/users";
    }
}
