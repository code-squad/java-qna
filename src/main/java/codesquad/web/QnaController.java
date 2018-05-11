package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnaController {

    @GetMapping("/hello")
    public String welCome(String name, int age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "welcome";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}



