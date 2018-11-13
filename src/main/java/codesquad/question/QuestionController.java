package codesquad.question;

import org.aspectj.weaver.ast.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = TestQuestions.addQuestions();

    @GetMapping("/")
    public String home(Model model) {
        Collections.reverse(questions);
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/questions")
    public String question(Question question) {
        question.setTime(getTodayDate());
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String detail(@PathVariable int index, Model model) {
        Question theQuestion = questions.get(index - 1);
        model.addAttribute("theQuestion", theQuestion);
        return "qna/show";
    }

    private String getTodayDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + time.format(today);
    }
}
