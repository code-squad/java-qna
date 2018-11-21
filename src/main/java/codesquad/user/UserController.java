package codesquad.user;

import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;  //db

    @PostMapping("")
    public String create(User user) {
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/profile/{pId}")
    public String userProfile(Model model, @PathVariable long pId) {
        model.addAttribute("user", userRepository.findById(pId).get());
        System.out.println(model);
        return "/user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/{pId}/form")
    private String userUpdateForm(Model model, @PathVariable long pId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/list";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (loginUser.matchPId(pId)) {
            model.addAttribute("user", userRepository.findById(pId).get());
            return "/user/updateForm";
        }
        return "redirect:/user/list";
    }

    @PutMapping("/{pId}/update")
    private String userUpdate(Model model, User updatedUser, @PathVariable long pId) {
        User user = userRepository.findById(pId).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("user", user);
        if (!user.matchPassword(updatedUser.getPassword())) {
            return "/user/update_failed";
        }
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/loginForm")
    private String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String userLogin(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            System.out.println("Wrong Id!");
            return "/user/login_failed";
        }
        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            System.out.println("Wrong Password!");
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

//    @PostMapping("/user/create")
//    public String create(String userId , String password, String name, String email) {
//        System.out.println("execute create!!");
//        System.out.println("userId : " + userId);
//        System.out.println("name : " + name);
//        System.out.println("password : " + password);
//        System.out.println("email : " + email);
//        return "user/index";
//    }
}
