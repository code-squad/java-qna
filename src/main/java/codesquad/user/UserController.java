package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//컨트롤러에서 컨트롤 하며 templates로 보낸다. static은 정적, 움직이지 않기 때문에 에러난다.
@Controller
@RequestMapping("/user")
public class UserController {

    //데이터에는 static을 절대~ 절대!!!!! 붙이면 안된다.
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

    @GetMapping("/profile/{id}")
    public String profile(Model model, @PathVariable Long id) {
        model.addAttribute("userProfile", userRepository.findById(id).orElse(null));
        return "user/profile";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable Long id) {
        model.addAttribute("userUpdate", userRepository.findById(id).orElse(null));
        return "user/update";
    }

    @PutMapping("/{id}")
    public String update(User newUser, @PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.updateForm(newUser);
        userRepository.save(user);
        return "redirect:/user/list";
    }
}
