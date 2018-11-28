package codesquad.user;

import codesquad.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    @PostMapping("/loginUser")
    public String loginUser(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
            }
            if (!user.matchPassword(password)) {
                return "redirect:/user/login";
            }
        }
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/password/{id}")
    public String password(Model model, @PathVariable Long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/password";
    }


    @PostMapping("/password/{id}")
    public String password(@PathVariable Long id, HttpSession session, String password, Model model) {
        model.addAttribute("user", id);
        User user = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (user.matchPassword(password)) {
            return "user/update";
        }
        return "redirect:/";
    }

//    @PutMapping("/{id}")
//    public String update(User user, HttpSession session) {
//        User loginUser = (User)session.setAttribute("loginUser");
//        return "redirect:/list";
//    }


    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("you can't access the other's profile.");
        }
        model.addAttribute("userUpdate", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return "user/update";
    }

    @PutMapping("/{id}")
    public String update(User newUser, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("you can't access the other's profile.");
        }
        User user = userRepository.findById(sessionedUser.getId()).orElse(null);
        userRepository.save(user.update(newUser));
        return "redirect:/user/list";
    }
}