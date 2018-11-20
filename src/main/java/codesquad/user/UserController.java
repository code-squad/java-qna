package codesquad.user;

import codesquad.HttpSessionUtils;
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

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

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
                session.setAttribute(HttpSessionUtils.USER_SESSION_KEY,user);
                return "redirect:/";
            }
        }
        return "/user/login_failed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(HttpSession session, Model model, @PathVariable long id) {
        System.out.println("수정");
        System.out.println(id);
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        if (!loginUser.matchId(id)) {
            model.addAttribute("users", userRepository.findAll());
            return "/user/list_failed";
        }
        model.addAttribute("user", loginUser);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(HttpSession session, User updatedUser) {
        System.out.println("업데이트");
        User loginUser =(User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (!loginUser.matchPassword(updatedUser)) {
            return "/user/update_failed";
        }
        loginUser.update(updatedUser);
        userRepository.save(loginUser);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        System.out.println("프로필");
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }

}
