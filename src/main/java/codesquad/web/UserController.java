package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            log.debug("login failed by user name");
            return "redirect:/users/loginForm";
        }

        if (!password.equals(user.getPassword())) {
            log.debug("login failed by password");
            return "redirect:/users/loginForm";
        }

        log.debug("login success!");
        session.setAttribute("user", user);

        return "redirect:/users/";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/join")
    public String join() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String create(User user){
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id ,Model model){
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("users", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, User editor) {
        User user = userRepository.findById(id).get();
        if (user.matchUser(user.getUserId())){
            user.updateInformation(editor, user.getPassword());
                userRepository.save(user);
        }
        return "redirect:/users/list";
    }
}
