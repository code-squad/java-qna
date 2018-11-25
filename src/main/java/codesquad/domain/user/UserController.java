package codesquad.domain.user;

import codesquad.domain.user.dao.UserRepository;
import codesquad.domain.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @PostMapping("/create")
    public String create(User user) {
        System.out.println("회원가입!");
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("회원가입 -> 아이디 중복!");
            // 중복가입에 대한 처리 후, 이전에 입력했던 데이터를 그대로 보관하는 방법을 잘 몰라서... 미적용... //
            // 세션보관은 뭔가.. 맞지 않아보임.. --> Ajax는 기억이 나지 않음... //
            return "redirect:/user/form";
        }
        return "redirect:/";
    }

    @PutMapping("/form/{id}")
    public String modify(@PathVariable Long id, User updatedUser) {
        System.out.println("회원정보 수정!");
        User user = userRepository.findById(id).orElse(null);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(Model model) {
        System.out.println("회원가입 페이지 이동!");
        model.addAttribute("actionPath", "/user/create");
        model.addAttribute("buttonName", "회원가입");
        model.addAttribute("methodType", "POST");
        return "/user/form";
    }

    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("회원전체목록 조회!");
        model.addAttribute("users", userRepository.findAll()); // 반환하는 웹 페이지에 변수를 전달
        return "/user/list";
    }

    @GetMapping("/profile/{id}")
    public String information(@PathVariable("id") Long id, Model model) {
        System.out.println("프로필 정보 확인");
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    public boolean isIdentification(User user, Long id) {
        return user.identification(id);
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model, HttpSession session) {
        System.out.println("회원정보 수정 페이지 이동!");
        User sessionUser = (User)session.getAttribute(Session.SESSION_NAME);
        if(sessionUser == null || !isIdentification(sessionUser, id)) {
            return "redirect:/user/loginForm";
        }
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        model.addAttribute("actionPath", String.format("/user/form/%d", Long.valueOf(id)));
        model.addAttribute("buttonName", "회원정보수정");
        model.addAttribute("methodType", "PUT");
        model.addAttribute("readOnly", "readonly");
        return "/user/form";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        System.out.println("로그인 화면 이동!");
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) {
        System.out.println("로그인 처리!");
        User user = userRepository.findByUserId(userId);
        if(!user.isValidPassword(password)) {
            System.out.println("로그인 실패!");
            return "/user/loginForm";
        }
        Session.registerSession(httpSession, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        System.out.println("로그아웃!");
        Session.removeSession(httpSession);
        return "redirect:/";
    }
}
