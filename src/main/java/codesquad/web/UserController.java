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
        log.info("로그인 페이지로 이동");
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        log.info("로그인 시도");

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/loginForm";
        }

        if (!password.equals(user.getPassword())) {
            return "redirect:/users/loginForm";
        }

        session.setAttribute("sessionedUser", user);

        log.info("/로 갑니다");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @PostMapping
    public String join(User user) {
        log.info("가입 시도");

        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        log.info("유저 리스트");

        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userRepository.getOne(userId));

        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Object tempUser = session.getAttribute("sessionedUser");
        if (tempUser == null) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User) tempUser;
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("You can't update the another user");
        }

        log.info("유저 수정");

        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    // 예외 처리 중요
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        Object tempUser = session.getAttribute("sessionedUser");
        if (tempUser == null) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User) tempUser;
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("You can't update the another user");
        }

        User user = userRepository.findOne(id);
        user.update(updatedUser);
        // 없으면 insert, 기존 사용자면 update
        userRepository.save(user);
        return "redirect:/users";
    }
}
