

package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class QuestionController {
    private static List<Question> questions = new ArrayList<>();
    static {
        questions.add(new Question("백경훈", "나는 잘생겼다.", "이것은 거짓이다.", String.valueOf(questions.size()+1)));
        questions.add(new Question("peter", "자바는 참 어렵다.", "하지만 재밌다.", String.valueOf(questions.size()+1)));
    }

    @PostMapping("/questions")
    public String questions(Question question) {
        System.out.println(question);
        System.out.println("AAA");
        question.setIndex(String.valueOf(questions.size()+1));
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        questions.sort(Comparator.comparing(Question::getIndex).reversed());
        model.addAttribute("questions",questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String profile(Model model, @PathVariable String index) {
        System.out.println(index);
        Question question = questions.stream()
                .filter(q -> q.getIndex().equals(index))
                .findFirst()
                .orElse(null);
        System.out.println(question);
        model.addAttribute("question", question);
        return "/qna/show";
    }

}
