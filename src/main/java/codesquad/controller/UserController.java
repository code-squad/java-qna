package codesquad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class UserController {
    private ArrayList<User> users = new ArrayList<>();

    @RequestMapping(value="/user/create", method=RequestMethod.POST)
    public String create(User user) {
        users.add(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }
}
