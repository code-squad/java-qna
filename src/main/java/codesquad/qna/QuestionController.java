package codesquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaForm() {
        return "qna/form";
    }

    @PostMapping("/create")
    public String create(Question question) {
        System.out.println("excute create!");
//        getQuestionRepository().create(question);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String showpage(@PathVariable Long index, Model model) {
        model.addAttribute("list", questionRepository.findById(index).orElse(null));
        return "qna/show";
    }
}
