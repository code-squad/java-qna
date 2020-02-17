package codesquad.study;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudyController {

    @GetMapping("/study")
    public String study(Model model) {
        model.addAttribute("Study", "Study용입니다.");
        return "study";
    }

}
