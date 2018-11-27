package codesquad.base;

import codesquad.base.qna.QnaRepository;
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
        System.out.println(qnaRepository.findAll());
        model.addAttribute("quests", qnaRepository.findAll());
        return "/index";
    }
}