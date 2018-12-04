package codesquad.user;

import codesquad.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;  //db

    @PostMapping("")
    public String create(User user) {
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/profile/{pId}")
    public String userProfile(Model model, @PathVariable long pId) {
        model.addAttribute("user", userRepository.findById(pId).get());
        return "/user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "/users/form";
    }

    @GetMapping("/{pId}/form")
    private String userUpdateForm(Model model, @PathVariable long pId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/list";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (loginUser.matchPId(pId)) {
            model.addAttribute("user", userRepository.findById(pId).get());
            return "/user/updateForm";
        }
        return "redirect:/users/list";
    }

    @PutMapping("/{pId}")
    private String userUpdate(Model model, User updatedUser, @PathVariable long pId) {
        User user = userRepository.findById(pId).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("user", user);
        if (!user.matchPassword(updatedUser)) {
            return "/user/update_failed";
        }
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/loginForm")
    private String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String userLogin(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            logger.debug("Wrong Id!");
            return "/user/login_failed";
        }
        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            logger.debug("Wrong Password!");
            return "/user/login_failed";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
