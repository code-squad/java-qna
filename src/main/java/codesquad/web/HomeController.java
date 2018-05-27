package codesquad.web;

import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String goHome(Model model, HttpSession session) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }
}
