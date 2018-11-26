package codesquad;

import codesquad.qna.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("questions", questionRepository.findByDeleted(false));
        return "index";
    }
}
