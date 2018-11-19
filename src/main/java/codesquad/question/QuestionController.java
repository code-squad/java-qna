package codesquad.question;

import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model, HttpSession session) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String eachQuestion(Model model, @PathVariable long id) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if(session.getAttribute("loginUser") != null) {
            System.out.println(session);
            return "qna/form";
        }
        return "redirect:/user/login";
    }
}