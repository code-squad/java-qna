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
    public String create(User user) {
        userRepository.save(user);
        System.out.println("유저생성");
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        System.out.println("프로필");
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }

 /*   @GetMapping("/users/{writer}")
    public String profileOfuserid(Model model, @PathVariable String writer) {
        System.out.println("프로필 유저아이디로 찾기");
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }*/


    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable long id) {
        System.out.println("수정");
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User newUser) {
        System.out.println("업데이트");
        User user = userRepository.findById(id).orElse(null);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }

}
