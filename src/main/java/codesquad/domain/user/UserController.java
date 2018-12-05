package codesquad.domain.user;

import codesquad.domain.user.dao.UserRepository;
import codesquad.domain.util.Session;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

/* Controller 역할 부여를 위한 어노테이션 */
@RequestMapping("/user")
@Controller
public class UserController {
    // Ram 메모리에 저장이 되기 때문에 서버 재구동 시, 모두 초기화 //
    // Ram 메모리는 가격이 비싸고, 빠른 처리속도 제공 //
    // 하드디스크는 가격이 싸고, 느린 처리속도 제공 //
    // 서버구동에도 영향을 받지않고 데이터를 조작하기 위해서는 데이터베이스 필요 //

    /* Post : 클라이언트에서 서버로 데이터를 보내는 방식 / Get : 클라이언트에서 서버로 데이터를 받는 방식 */

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = getLogger(UserController.class);
    
    @PostMapping("")
    public String create(User user) {
        logger.info("회원가입!");
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.info("회원가입 -> 아이디 중복!");
            return "redirect:/user/form";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String modify(@PathVariable Long id, User updatedUser) {
        logger.info("회원정보 수정!");
        User user = userRepository.findById(id).orElse(null);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/joinForm")
    public String form(Model model) {
        logger.info("회원가입 페이지 이동!");
        model.addAttribute("actionPath", "/user");
        model.addAttribute("buttonName", "회원가입");
        model.addAttribute("methodType", "POST");
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
        if(!Session.isUser(session, id)) {
            return "redirect:/user/loginForm";
        }
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        model.addAttribute("actionPath", String.format("/user/%d", Long.valueOf(id)));
        model.addAttribute("buttonName", "회원정보수정");
        model.addAttribute("methodType", "PUT");
        model.addAttribute("readOnly", "readonly");
        return "/user/form";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        logger.info("로그인 화면 이동!");
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) {
        logger.info("로그인 처리!");
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            logger.info("잘못된 아이디!");
            return "/user/login_failed";
        }
        if(!user.isValidPassword(password)) {
            logger.info("잘못된 패스워드!");
            return "/user/login_failed";
        }
        Session.registerSession(httpSession, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        logger.info("로그아웃!");
        Session.removeSession(httpSession);
        return "redirect:/";
    }
}
