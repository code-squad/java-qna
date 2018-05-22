package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log =  LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping()
    public String questions(String title, String contents, HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessoinUser = SessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessoinUser, title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        log.debug("/questions/form/success");
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String viewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));

        return "qna/show";
    }
}