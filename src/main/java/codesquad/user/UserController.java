package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

//컨트롤러에서 컨트롤 하며 templates로 보낸다. static은 정적, 움직이지 않기 때문에 에러난다.
@Controller
public class UserController {

    private static List<User> users = new ArrayList<>();

    static {
        User user = new User();
        user.setName("siro");
        user.setEmail("kuro.hotmail.com");
        user.setPassword("111");
        user.setUserId("siro");
        users.add(user);
    }

    @PostMapping("/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        User userProfile = null;
        for (User user : users) {
//          String이기 때문에 == 은 안되고 equals가 된다.
            if (user.getUserId().equals(userId)) {
                userProfile = user;
            }
        }
        model.addAttribute("userProfile", userProfile);
        return "user/profile";
    }

    @GetMapping("/user/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("user/login")
    public String login() {
        return "/user/login";
    }


}
