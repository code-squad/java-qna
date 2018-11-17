package codesquad.user;

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
    private UserRepository userRepository;

    @PostMapping
    public String create(User user){
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showModifyView(@PathVariable long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User modifiedUser){
        User user = userRepository.findById(id).get();
        user.update(modifiedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
