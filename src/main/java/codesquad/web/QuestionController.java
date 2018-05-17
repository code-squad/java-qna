package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String saveQuestion(Question question){
        questionRepository.save(question);
        System.out.println(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String goHome(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String goHome(@PathVariable Long id, Model model){
        model.addAttribute("question", questionRepository.findOne(id));
        return "qna/show";
    }
}
