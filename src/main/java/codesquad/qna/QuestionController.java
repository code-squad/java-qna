package codesquad.qna;

import codesquad.HttpSessionUtils;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) return "/user/login";
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = Question.newInstance(loginUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/questions";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) return "/user/login";
        return "/qna/form";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{index}")
    public String show(Model model, @PathVariable Long index) {
        model.addAttribute("question", questionRepository.findById(index).orElse(null));
        return "/qna/show";
    }
}
