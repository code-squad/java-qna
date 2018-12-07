package codesquad.web;

import codesquad.HttpSessionUtils;
import codesquad.exception.UserException;
import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("")
    public String create(User user) {
        logger.info("user create");
        userRepository.save(user);
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
        logger.info("login user");
        User loginUser = userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(() -> new UserException("비밀번호나 아이디가 다릅니다."));
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY,loginUser);
        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("logout user");
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(HttpSession session, Model model, @PathVariable long id) {
        logger.info("user update form");
        HttpSessionUtils.isLoginUser(session);

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        loginUser.matchId(id);

        model.addAttribute("user", loginUser);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(HttpSession session, User updatedUser) {
        logger.info("update user");

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        loginUser.update(updatedUser);

        userRepository.save(loginUser);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        logger.info("user profile");
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("아이디가 없습니다."));
        model.addAttribute("user", user);
        return "user/profile";
    }


}
