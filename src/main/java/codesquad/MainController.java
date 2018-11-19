package codesquad;

import codesquad.Qna.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private QnaRepository qnaRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("quests", qnaRepository.findAll());
        return "/index";
    }
}
