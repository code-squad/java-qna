package codesquad.qna;

import codesquad.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
public class QnAController {

    private List<Question> questions = new ArrayList<>();

    @PostMapping("qna/inquire")
    public String inquire(Question question) {
        System.out.println("Call question Method");
        question.setNo(getQuestionNo());
        System.out.println(question.toString());
        questions.add(question);
        return "redirect:/list";
    }

    public int getQuestionNo() {
        return questions.size();
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/qna/show/{no:.+}")
    public String detail(@PathVariable int no, Model model) {
        System.out.println("Call detail Method");
        model.addAttribute("question", obtainQuestion(no));
        return "/qna/show";
    }

    public Question obtainQuestion(int no) {
        for(Question question : questions) {
            if(question.isQuestion(no)) {
                return question;
            }
        }
        return null;
    }
}
