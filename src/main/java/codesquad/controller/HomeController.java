package codesquad.controller;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/")
    public String home(Model model) {
        List<Question> questions = questionRepo.findAll();
        model.addAttribute("questions", questions.stream().filter(question-> !question.isDelete()).collect(toList()));
        return "index";
    }
}
