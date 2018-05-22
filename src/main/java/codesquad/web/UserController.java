package codesquad.web;


import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.web.SessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log =  LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "list";
    }

    @PostMapping()
    public String create(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));

        return "profile";
    }


    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        // session에 없는 user이면 수정을 진행할 수 없다.
        if (!SessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = SessionUtils.getUserFromSession(session);
        if (!sessionedUser.isMatchedId(id)) {
            throw new IllegalStateException("You can't update : 자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("user", userRepository.findOne(id));

        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUserData(@PathVariable Long id, User updateUser, String newPassword, HttpSession session) {
        log.debug("updateUser : {}", updateUser);

        if (!SessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        if (!sessionUser.isMatchedId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        User user = userRepository.findOne(id);
        if (!user.update(updateUser, newPassword)) {
            return "/passwordError";
        }
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
            log.debug("Login Failure : user null");
            return "redirect:/users/loginForm";
        }

        if (!user.isMatchedPassword(password)) {
            log.debug("Login Failure : password mismatch");
            return "redirect:/users/loginForm";
        }

        log.debug("Login success !!");
        // 동시에 서버에 접속하여 세션을 부여 받는 경우 세션 풀에 sessionedUser가 중복되지 않는가?
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }
}
