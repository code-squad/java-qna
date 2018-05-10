package codesquad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private Users users = new Users();

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String create(User user) {
        users.addUser(user);
        return "redirect:/users";
    }

    @RequestMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }
}
