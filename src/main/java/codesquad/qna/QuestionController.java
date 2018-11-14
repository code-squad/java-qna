package codesquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static codesquad.qna.QuestionRepository.getQuestionRepository;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @GetMapping("/form")
    public String qnaForm() {
        return "qna/form";
    }

    @PostMapping("/create")
    public String create(Question question) {
        System.out.println("excute create!");
        getQuestionRepository().create(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String showpage(@PathVariable int index, Model model) {
        model.addAttribute("list", getQuestionRepository().checkSameId(index));
        return "qna/show";
    }
}
