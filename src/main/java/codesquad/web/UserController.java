package codesquad.web;

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
        return "redirect:/users/list";
    }

    // get은 가진 것을 뿌려줌
    @GetMapping("/users/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String showUser(Model model, @PathVariable("userId") String userId) {
        System.out.println("User id : " + userId);
        System.out.println("Users size : " + users.getSize());
        User user = users.findUser(userId);
        System.out.println(user == null);
        System.out.println("find user : " + user.getUserId());
        model.addAttribute("user", user);
        return "profile";
    }

}
