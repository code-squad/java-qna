package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsController {


    @GetMapping("/")
    public String welcome(String name, Integer age, Model model){
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "index";
    }
}
