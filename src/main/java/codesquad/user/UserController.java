package codesquad.user;

import codesquad.utility.HttpSessionUtils;
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

    @GetMapping("/singUp")
    public String signUp() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId).filter(user -> user.matchPassword(password));
        if (maybeUser.isPresent()) {
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, maybeUser.get());
            return "redirect:/";
        }
        return "/user/login_failed";
    }

    @PostMapping("")
    public String userCreate(User user) {
        userRepository.save(user);
        return "redirect:/";       //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("")
    public String showMemberList(Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String showPersonalInformation(@PathVariable long id, Model model) {
        model.addAttribute("usersProfile", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updatePersonalInformation(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isMatchId(id)) {
            return "/users";
        }
        model.addAttribute("usersInformation", userRepository.findById(id).orElse(null));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String redirect(@PathVariable long id, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User user = userRepository.findById(id).orElse(null);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

}
