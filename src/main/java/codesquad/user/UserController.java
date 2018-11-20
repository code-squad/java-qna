package codesquad.user;

import codesquad.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static codesquad.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(User user) {
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        if(!loggedInUser.isMatchId(id)) {
            return "/error/access";
        }

        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "/user/update_form";
    }

    @PutMapping("/{id}")
    public String update(User updatedUser, HttpSession session) {
        User loggedInUser = HttpSessionUtils.getUserFromSession(session);

        if(!loggedInUser.isMatchPassword(updatedUser.getPassword())) {
            return "user/update_failed";
        }

        loggedInUser.update(updatedUser);
        userRepository.save(loggedInUser);
        return "redirect:/users/";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);

        if(!maybeUser.isPresent()) {
            System.out.println("아이디 불일치");
            return "/user/login_failed";
        }

        if(!maybeUser.get().isMatchPassword(password)) {
            System.out.println("비밀번호 불일치");
            return "/user/login_failed";
        }

        System.out.println("로그인 성공");
        session.setAttribute(USER_SESSION_KEY, maybeUser.get());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }
}

//    MvcConfig에서 지정
//    @GetMapping("/login")
//    public String loginForm() {
//        return "/user/login";
//    }

//    @GetMapping("/{id}")
//    public ModelAndView show(@PathVariable long id) {
//        ModelAndView mav = new ModelAndView("user/profile");
//        mav.addObject("user", userRepository.findById(id).orElse(null));
//        return mav;
//    }
