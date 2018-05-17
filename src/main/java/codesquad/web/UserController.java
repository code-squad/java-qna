package codesquad.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
    public String create(User user) {
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String view(Model model) {
        System.out.println(userRepository.count());
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findOne(id);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String searchUser(@PathVariable Long id, Model model) {
        User user = userRepository.findOne(id);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User updateUser, String checkPassword) {
        System.out.println("checkPassword : " + checkPassword);
        User user = userRepository.findOne(id);
        user.updateUser(updateUser, checkPassword);
        userRepository.save(user);
        return "redirect:/users";
    }
}
