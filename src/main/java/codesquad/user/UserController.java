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
        System.out.println("유저생성");
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> mybeUser = userRepository.findByUserId(userId);
        if (mybeUser.isPresent()) {
            User user = mybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute("loginUser",user);
                return "redirect:/";
            }
        }
        return "/user/login_failed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }

/*    @GetMapping("/{writer}")
    public String profileOfuserid(Model model, @PathVariable String writer) {
        System.out.println("프로필 유저아이디로 찾기");
        User user = userRepository.findByUserId(writer);
        model.addAttribute("user", user);
        return "user/profile";
    }*/

    @GetMapping("/{id}/form")
    public String updateForm(HttpSession session, Model model, @PathVariable long id) {
        System.out.println("수정");
        User loginUser =(User) session.getAttribute("loginUser");
        if (loginUser.matchId(id)) {
            User user = userRepository.findById(id).orElse(null);
            model.addAttribute("user", user);
            return "user/updateForm";
        }
        model.addAttribute("users", userRepository.findAll());
        return "/user/list_failed";
    }

    @PutMapping("/{id}")
    public String update(HttpSession session, User newUser) {
        System.out.println("업데이트");
        User loginUser =(User) session.getAttribute("loginUser");
        if (loginUser.matchPassword(newUser.getPassword())) {
            loginUser.update(newUser);
            userRepository.save(loginUser);
            return "redirect:/users";
        }
        return "/user/update_failed";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        System.out.println("프로필");
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }

}
