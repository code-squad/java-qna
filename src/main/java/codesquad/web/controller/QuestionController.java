package codesquad.web.controller;

import codesquad.web.domain.Question;
import codesquad.web.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    private List<Question> questions = new ArrayList<>();

    @PostMapping("/qna/submit")
    public String question(Question question) {
        System.out.println(question.toString());
        questions.add(question);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAll(new Sort(Sort.Direction.DESC, "id")));
        return "index";
    }

    @GetMapping("/qna/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        return "qna/show";
    }

}
