package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(Model model, @PathVariable Long id) {


        /* // optional 설명 2018-11-19
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            //null일 때
        }
        User user1 = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        // 사용자가 무조건 있어야 한다. 없는 것은 말이 안될 경우에 exception

        User user2 = userRepository.findById(id).orElse(null);
        // 사용자가 있으면 할당, 없으면 null
        */

        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable Long id, User user) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    /*
    @PutMapping("/{id}")
    public String update1(User user, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        return "redirect:/users";
    }
    */

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute("loginUser", user);
                return "redirect:/";
            }
        }
        return "redirect:/user/login";
    }
}
