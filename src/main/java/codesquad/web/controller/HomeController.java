package codesquad.web.controller;

import codesquad.web.domain.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAllByOrderByIdDesc());
        return "index";
    }

}
