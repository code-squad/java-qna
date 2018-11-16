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

    @PostMapping("")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("해당 id를 찾을 수 없습니다"));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showUserInfo(@PathVariable Long id, Model model) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("해당 id를 찾을 수 없습니다"));
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUserInfo(@PathVariable Long id, User userUpdated) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("해당 id를 찾을 수 없습니다"));
        user.update(userUpdated);
        userRepository.save(user);
        return "redirect:/users";
    }

}
