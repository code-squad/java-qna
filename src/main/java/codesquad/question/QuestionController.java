package codesquad.question;

import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/create")
    public String create(Question question, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(loginUser.getUserId());
        questionRepository.save(question);
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String questionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/show")
    public String questionShow() {
        return "/qna/show";
    }

    @GetMapping("/{pId}")
    public String questionContents(Model model, @PathVariable long pId) {
        model.addAttribute("question", questionRepository.findById(pId).get());
        return "/qna/show";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (HttpSessionUtils.isLoginUser(session)) {
            return "/qna/form";
        }
        return "user/login";
    }
}
