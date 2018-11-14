package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public String create(User user) {
        user.setIndex(UserRepository.getUsers().size() + 1);
        UserRepository.addUser(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", UserRepository.getUsers());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        model.addAttribute("user", UserRepository.findMatchIdUser(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateProfile(Model model, @PathVariable String userId) {
        model.addAttribute("user", UserRepository.findMatchIdUser(userId));
        return "/user/updateForm";
    }

    //todo : users로 create와 구분 어떻게? post, /users, parameter User 모두 동일한데...
    @PostMapping("/update")
    public String updateUser(User updatedUser) {
        UserRepository.findMatchIdUser(updatedUser).updateUserProfile(updatedUser);
        return "redirect:/users";
    }
}
