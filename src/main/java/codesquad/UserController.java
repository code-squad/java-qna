package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/create")
    public String create(String userId, String password, String name, Integer age, String email, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("password", password);
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("email", email);
        return "hello";
    }
}
