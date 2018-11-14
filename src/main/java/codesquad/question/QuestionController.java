package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private List<Question> questions = TestQuestions.addQuestions();

    @GetMapping
    public String home(Model model) {
        Comparator<Question> comp = (q1, q2) -> -1;
        List sortedQuestions = questions.stream().sorted(comp.reversed()).collect(Collectors.toList());
        model.addAttribute("questions", sortedQuestions);
        return "index";
    }

    @PostMapping
    public String question(Question question) {
        question.setTime(getTodayDate());
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/questions";
    }

    @GetMapping("/{index}")
    public String detail(@PathVariable int index, Model model) {
        System.out.println("index : " + index);
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
