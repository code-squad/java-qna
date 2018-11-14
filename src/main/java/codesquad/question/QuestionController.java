package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/question/create")
    public String create(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/question/list";
    }

    @GetMapping("/question/list")
    public String questionList(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/question/show")
    public String questionShow() {
        return "/qna/show";
    }

    @GetMapping("/question/{index}")
    public String questionContents(Model model, @PathVariable int index) {
        model.addAttribute(questions.get(index - 1));
        return "qna/show";
    }

//    @GetMapping("/question/{title}")
//    public String questionContents(Model model, @PathVariable String title) {
//        for (Question question : questions) {
//            if (question.getTitle().equals(title)) model.addAttribute(question);
//        }
//        return "qna/show";
//    }
}
