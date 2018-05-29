package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String form(Question question) {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(Question question){
        question.setIndex(questionRepository.findAll().size()+1);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getBackToIndex(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String getBackToIndex(@PathVariable Long index, Model model){
        model.addAttribute("question", questionRepository.findById(index));
        return "show";
    }
}

