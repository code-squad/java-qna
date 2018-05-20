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
    private static final String ANOTHER_USER_UPDATE_MSG = "You can't update the another user";
    private static final String REDIRECT_USERS_LOGIN_FORM = "redirect:/users/loginForm";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // TODO 다른 사용자의 정보를 수정하려는 경우 에러 페이지를 만든 후 에러 메시지를 출력한다.

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        log.info("로그인 시도");
        User user = userRepository.findByUserId(userId);
        if (!checkValidLogin(password, user)) return REDIRECT_USERS_LOGIN_FORM;

        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        log.info("/로 갑니다");
        return "redirect:/";
    }

    private boolean checkValidLogin(String password, User user) {
        if (user == null) {
            return false;
        }

        if (!user.matchPassword(password)) {
            return false;
        }
        return true;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
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
        log.info("유저 프로필");
        model.addAttribute("user", userRepository.getOne(userId));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        log.info("유저 수정 폼");
        if (!checkSessionById(id, session)) return REDIRECT_USERS_LOGIN_FORM;

        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    // 예외 처리 중요
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        log.info("유저 수정 실행");
        if (!checkSessionById(id, session)) return REDIRECT_USERS_LOGIN_FORM;

        User user = userRepository.findOne(id);
        user.update(updatedUser);
        // 없으면 insert, 기존 사용자면 update
        userRepository.save(user);
        return "redirect:/users";
    }

    private boolean checkSessionById(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return false;
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException(ANOTHER_USER_UPDATE_MSG);
        }
        return true;
    }
}
