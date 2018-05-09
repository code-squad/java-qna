package codesquad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(value="/user/create", method=RequestMethod.POST)
    public String create(User user) {
        System.out.println(user);
        return "index";
    }
}
