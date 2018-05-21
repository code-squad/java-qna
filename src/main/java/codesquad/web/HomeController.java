package codesquad.web;

import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private QuestionRepository questionRepository;
    //Autowired가 있어야 DB에 nullpoint에러가 안발생한다.
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }
}
