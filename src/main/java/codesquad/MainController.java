package codesquad;

import codesquad.qna.QnaController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", QnaController.questions);
        return "index";
    }

}
