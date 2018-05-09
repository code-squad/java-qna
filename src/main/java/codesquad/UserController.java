package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/create")
    public String create(String userId, String password, String name, String email, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("password", password);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "hello";
    }
}
