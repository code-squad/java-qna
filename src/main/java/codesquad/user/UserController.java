package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("user/create")
    public String create(User user){
        System.out.println("execute create!!");
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:/users";       //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users",users);
        return "user/list";
    }
}
