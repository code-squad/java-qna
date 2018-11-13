package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    //todo: 싱글톤패턴, questions 객체 하나만 생성하여 이용
    @PostMapping("/question/create")
    public String create(Question question) {
        question.setIndex(QuestionRepository.getQuestions().size() + 1);
        QuestionRepository.addQuestion(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", QuestionRepository.getQuestions());
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String eachQuestion(Model model, @PathVariable int index) {
        model.addAttribute("question", QuestionRepository.getQuestions().get(index - 1));
        return "/qna/show";
    }
}