

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
    private static List<Question> questions = new ArrayList<>();
    static {
        Question question1 = new Question();
        Question question2 = new Question();
        question1.setTitle("qqqq");
        question2.setTitle("aaaa");
        question1.setWriter("pobi");
        question2.setWriter("java");
        question1.setContents("123");
        question2.setContents("qweasd");
        question1.setIndex(String.valueOf(questions.size()+1));
        questions.add(question1);
        question2.setIndex(String.valueOf(questions.size()+1));
        questions.add(question2);
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
        System.out.println("getquestions");
        model.addAttribute("questions",questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String profile(Model model, @PathVariable String index) {
        Question question = questions.stream()
                .filter(q -> q.getIndex().equals(index))
                .findFirst()
                .orElse(null);
        model.addAttribute("question", question);
        return "/qna/show";
    }

}
