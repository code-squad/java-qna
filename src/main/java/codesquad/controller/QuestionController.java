package codesquad.controller;

import codesquad.model.Question;
import codesquad.model.Questions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class QuestionController {
    private Questions questions = new Questions();

    @RequestMapping(value = "/qna/create", method = RequestMethod.POST)
    public String create(Question question) {
        questions.add(question);
        System.out.println(question);
        return "redirect:/";
    }

    @RequestMapping("/qna/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Optional<Question> question = questions.findById(id);
        if (!question.isPresent()) {
            System.out.println("존재하지않는 게시글임"); // inner log
            return "/error/show";
        }
        model.addAttribute("question", question.get());
        return "/qna/show";
    }

    @RequestMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questions.getQuestions());
        return "index";
    }
}
