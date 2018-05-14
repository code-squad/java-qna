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
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(User usr) {
        userRepository.save(usr);
        return "redirect:/users";
    }
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        model.addAttribute("users", userRepository.findOne(id));
        return "/user/profile";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Object tempUser = session.getAttribute("sessionedUser");
        if (tempUser == null)
            return "redirect:/users/loginForm";

        User sessionedUser = (User)tempUser;
        if (!id.equals(sessionedUser.getId()))
            throw new IllegalStateException("You can't update the another user");

        User user = userRepository.findOne(id);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser , HttpSession session) {
        Object tempUser = session.getAttribute("sessionedUser");
        if (tempUser == null)
            return "redirect:/users/loginForm";

        User sessionedUser = (User)tempUser;
        if (!id.equals(sessionedUser.getId()))
            throw new IllegalStateException("You can't update the another user");

        User user = userRepository.findOne(id);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            logger.debug("login failed user is null");
            return "redirect:/users/loginForm";
        }

        if (!password.equals(user.getPassword())) {
            logger.debug("login failed");
            return "redirect:/users/loginForm";
        }
        logger.debug("login success");
        session.setAttribute("sessionedUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        logger.debug("logout ok");
        return "redirect:/";
    }
}
