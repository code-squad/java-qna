package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

//    @GetMapping("/{id}")
//    public ModelAndView show(@PathVariable long id) {
//        ModelAndView mav = new ModelAndView("user/profile");
//        mav.addObject("user", userRepository.findById(id).orElse(null));
//        return mav;
//    }

    @GetMapping("/{id}/form")
    public String updateProfile(Model model, @PathVariable long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/update_form";
    }

    //todo 로그인한 사용자가 자기 자신의 정보를 업데이트 할 때만 수정이 되도록

//    @PutMapping("/{id}")
//    public String updateUser(User updatedUser, @PathVariable long id) {
//        User user = userRepository.findById(id).orElse(null);
//        user.update(updatedUser);
//        userRepository.save(user);
//        return "redirect:/users";
//    }

    @PutMapping("/{id}")
    public String updateUser(User updatedUser, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        if(loginUser != null && loginUser.matchPassword(updatedUser)) {
            loginUser.update(updatedUser);
            userRepository.save(loginUser);
            return "redirect:/users/{id}";
        }
        return "/user/update_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            if(user.matchPassword(password)) {
                //세션을 쓰자 HttpSession이용, 자동으로 담아서 클라이언트에 전달한다
                //DB에 저장하는게 아니고 톰캣 서버상의 파일시스템에 저장한다.(설정 통해서 디비저장도 되긴함)
                session.setAttribute("loginUser", user);
                return "redirect:/";
            }
        }
        return "/user/login_failed";
    }
}
