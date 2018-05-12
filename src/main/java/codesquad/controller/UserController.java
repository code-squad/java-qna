package codesquad.controller;

import codesquad.model.User;
import codesquad.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class UserController {
    private Users users = new Users();

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String create(User user) {
        users.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/list")
    public String getList(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @RequestMapping("/user/{userId}")
    public String getInfo(Model model, @PathVariable("userId") String userId) {
        Optional<User> user = users.findById(userId);
        if (!user.isPresent()) {
            System.out.println("존재하지않는 사용자입니다.");
            return "/error/show";
        }
        model.addAttribute("user", user.get());
        return "/user/profile";
    }
}
