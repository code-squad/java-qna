package codesquad.web.controller;

import codesquad.web.model.User;
import codesquad.web.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private Users users = new Users();

    // post는 받아서 전달
    @PostMapping("/create")
    public String create(User user) {
        System.out.println("User : " + user);
        users.addUser(user);
        return "redirect:/users"; // 얘는 get맵핑이 되어 있어야 함.
    }

    // get은 가진 것을 뿌려줌
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        System.out.println(users.toString());
        return "user/list";
    }

    @GetMapping("/users/:{userId}")
    public String showUser(Model model, @PathVariable("userId") String userId) {
        User user = users.findUser(userId);
        System.out.println("find user : " + user.getUserId());
        model.addAttribute("user", user);
        return "user/profile";
    }

}
