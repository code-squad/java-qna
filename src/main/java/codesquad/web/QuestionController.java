package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(String title, String contents, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getBackToIndex(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String getBackToIndex(@PathVariable Long index, Model model){
        model.addAttribute("question", questionRepository.findById(index));
        return "show";
    }
}

