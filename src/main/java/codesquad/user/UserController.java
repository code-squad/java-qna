package codesquad.user;

import codesquad.utility.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/singUp")
    public String signUp() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User maybeUser = userRepository.findByUserId(userId);
        if (maybeUser != null && maybeUser.matchPassword(password)) {
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, maybeUser);
            return "redirect:/";
        }
//        userRepository.findByUserId(userId).filter(u->u.matchPassword(password)).orElse(null); // findByUserId의 반환값이 optional일때 stream을 쓸수 있다.
        return "/user/login";
    }

    @PostMapping("")
    public String userCreate(String userId, User user) {
        User findUser = userRepository.findByUserId(userId);
        if (findUser != null) {                     // null이 아니면 아이디가 있는 것이므로 에러가 나와야 한다.
            throw new IllegalArgumentException("아이디가 중복 합니다.");
        }
        userRepository.save(user);
        return "redirect:/";       //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("/list")
    public String showMemberList(Model model, HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            return "/user/login";
        }
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String showPersonalInformation(@PathVariable long id, Model model) {
        model.addAttribute("usersProfile", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updatePersonalInformation(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {       // 로그인 안한 상태로 수정을 하려면 로그인을 먼저 해야 한다.
            return "/user/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {                       // 로그인한 사용자가 다른 사람의 정보를 수정할 수 없다.
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }

//        userRepository.findById(id).orElseThrow(()->new IllegalArgumentException());  //사용자가 존재하지 않으면 에러 반환후 중단
        model.addAttribute("usersInformation", userRepository.findById(id).orElse(null));    //사용자가 존재 하지 않으면 null반환
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String redirect(@PathVariable long id, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            return "/user/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }

//        User loginUser = (User) session.getAttribute("loginUser");   //오늘 수업을 통해 세션을 통해 넣고 빼는 것을 배웠다.
        User user = userRepository.findById(id).orElse(null);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

}
