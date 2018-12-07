package codesquad.domain.user;

import codesquad.domain.user.dao.UserRepository;
import codesquad.domain.util.Result;
import codesquad.domain.util.Session;
import codesquad.domain.util.SessionMaintenanceException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

import static org.slf4j.LoggerFactory.getLogger;

@RequestMapping("/users")
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = getLogger(UserController.class);

    @PutMapping("/{id}")
    public String modify(@PathVariable Long id, User updatedUser, HttpSession httpSession, Model model) {
        try {
            Session.isSession(httpSession);
            logger.info("회원정보 수정!");
            User user = userRepository.findById(id).orElse(null);
            user.update(updatedUser);
            userRepository.save(user);
            return "redirect:/";
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다!"));
            return "/user/login_failed";
        }
    }

    @GetMapping("/joinForm")
    public String form(Model model) {
        logger.info("회원가입 페이지 이동!");
        model.addAttribute("actionPath", "/users");
        model.addAttribute("buttonName", "회원가입");
        model.addAttribute("methodType", "POST");
        model.addAttribute("buttonEnable","disabled");
        model.addAttribute("formName", "join");
        return "/user/form";
    }

    @GetMapping()
    public String list(Model model) {
        logger.info("회원전체목록 조회!");
        model.addAttribute("users", userRepository.findAll()); // 반환하는 웹 페이지에 변수를 전달
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String information(@PathVariable("id") Long id, Model model) {
        logger.info("프로필 정보 확인");
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/joinForm")
    public String modify(@PathVariable("id") Long id, Model model, HttpSession session) {
        logger.info("회원정보 수정 페이지 이동!");
        try {
            model.addAttribute("user", userRepository.findById(id).orElse(null));
            model.addAttribute("actionPath", String.format("/users/%d", Long.valueOf(id)));
            model.addAttribute("buttonName", "회원정보수정");
            model.addAttribute("methodType", "PUT");
            model.addAttribute("readOnly", "readonly");
            model.addAttribute("result", Result.ok());
            model.addAttribute("formName", "update");
        } catch (NullPointerException npe) {
            model.addAttribute("result", Result.fail("본인만 이용가능한 서비스입니다!"));
            return "/user/login_failed";
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인후에 서비스를 이용하세요!"));
            return "/user/login_failed";
        }
        return "/user/form";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        logger.info("로그인 화면 이동!");
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession, Model model) {
        try {
            logger.info("로그인 처리!");
            Session.registerSession(httpSession, userRepository.findByUserId(userId));
            return "redirect:/";
        } catch (FailureTypeException fte) {
            logger.info("잘못된 패스워드를 입력했습니다!");
            model.addAttribute("result", Result.fail("잘못된 패스워드를 입력했습니다!"));
            return "/user/login_failed";
        } catch (NullPointerException npe) {
            logger.info("잘못된 아이디를 입력했습니다!");
            model.addAttribute("result", Result.fail("잘못된 아이디를 입력했습니다!"));
            return "/user/login_failed";
        }
    }

    @GetMapping("/loginFail")
    public String loginFail(Model model) {
        logger.info("로그인 실패 화면 이동!");
        model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다!"));
        return "/user/login_failed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        logger.info("로그아웃!");
        Session.removeSession(httpSession);
        return "redirect:/";
    }
}
