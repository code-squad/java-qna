package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(Model model, @PathVariable("id") Long id) {
        System.out.println(userRepository.findById(id));
        model.addAttribute("user", userRepository.findById(id));
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        /*
        model.addAttribute(
                UserRepository.getInstance().getUsers()
                        .stream().filter(x -> x.getUserId().equals(userId)).findFirst().orElse(null));
                        */
        return "user/profile";

    }
}
