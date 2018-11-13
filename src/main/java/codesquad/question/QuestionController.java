/*
package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private static List<Question> questions = new ArrayList<>();

    @GetMapping("/questions/form")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }

    @PostMapping("/questions")
    public String list() {

        return "/";
    }

}
*/

package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        questions.add(question1);
        questions.add(question2);
    }

    @PostMapping("/questions")
    public String questions(Question question) {
        System.out.println(question);
        System.out.println("AAA");
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        System.out.println("getquestions");
        model.addAttribute("questions",questions);
        return "index";
    }

}
