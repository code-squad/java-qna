package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/join")
    public String join() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String create(User user){
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId ,Model model){
        for (User user:userRepository.findAll()) {
            if (user.getUserId().equals(userId)){
                model.addAttribute("user", userRepository.findAll());
            }
        }
        return "profile";
    }

    @GetMapping("/{userId}/form")
    public String update(@PathVariable String userId, Model model) {
        for (User user:userRepository.findAll()) {
            if (user.getUserId().equals(userId)){
                model.addAttribute("users", userRepository.findAll());
            }
        }
        return "/user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable String userId, User editor) {
        List<User> users = userRepository.findAll();
        for (User user: users) {
            if (user.matchUser(userId)){
                user.updateInformation(editor, user.getPassword());
                userRepository.save(user);
                break;
            }
        }
        return "redirect:/users/list";
    }
}
