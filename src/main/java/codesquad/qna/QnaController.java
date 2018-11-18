package codesquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public String create(Question question){
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping
    public String showQuestions(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable long id, Model model){
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }
}
