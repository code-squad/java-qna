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

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id ,Model model){
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("users", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, User editor) {
        User user = userRepository.findById(id).get();
        if (user.matchUser(user.getUserId())){
            user.updateInformation(editor, user.getPassword());
                userRepository.save(user);
        }
        return "redirect:/users/list";
    }
}
