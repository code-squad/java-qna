package codesquad.web.controller;

import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    // post는 받아서 전달
    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        log.info("Create user : {} ", user.toString());
        return "redirect:/users"; // 얘는 get맵핑이 되어 있어야 함.
    }

    // get은 가진 것을 뿌려줌
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        Optional<User> maybeUser = userRepository.findById(id);
        User user = maybeUser.orElseThrow(RuntimeException::new);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String beforePassword, User updateUser) {
        userRepository.findById(id)
                .filter(u -> u.update(beforePassword, updateUser))
                .ifPresent(u -> userRepository.save(u));
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        log.info("login - id : {}, pw : {}", userId, password);
        return userRepository.findByUserId(userId)
                .filter(u -> u.isMatch(password))
                .map(u -> {
                    session.setAttribute("user", u);
                    return "redirect:/";})
                .orElseGet(() -> {
                    return "redirect:/users/loginForm";
                });
    }

}
