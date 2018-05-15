package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String questions(Question question) {
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String viewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));

        return "qna/show";
    }

    @GetMapping({"/", "/index"})
    public String welcome(Model model) {
        model.addAttribute("posts", questionRepository.findAll(new Sort(Sort.Direction.DESC, "id")));

        return "index";
    }
}