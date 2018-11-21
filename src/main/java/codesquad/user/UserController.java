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
    UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String readProfiles(Model model, @PathVariable String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", user.get());
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "user/update_form";
    }

    @PutMapping("/{userId}")
    public String update(@PathVariable String userId, User updateUserInfo, HttpSession session) {
        User loginUser = (User) session.getAttribute(User.SESSION_NAME);
        Optional<User> user = userRepository.findByUserId(userId);

        if(loginUser!=null && loginUser.checkId(userId) && updateUserInfo.checkPassword(user)){
            updateUserInfo.fillEmpty(loginUser);
            userRepository.save(updateUserInfo);
            return "redirect:/users";
        }
        return "redirect:/error";
    }

    @GetMapping("login")
    public String login(){
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (new User(null, password, null, null).checkPassword(maybeUser)) {
            session.setAttribute(User.SESSION_NAME, maybeUser.get());
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(User.SESSION_NAME);
        return "redirect:/";
    }
}
