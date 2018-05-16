package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("questions")
public class QuestionController {
    @Autowired
    QuestionRepository qnaRepository;
    // Autowired가 없으면 nullpoint가 발생을 하게된다.

    @GetMapping("/form")
    public String getForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        qnaRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getQnA(@PathVariable Long id, Model model) {
        model.addAttribute("questions", qnaRepository.findOne(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findOne(id);
        if (!question.isSameWriter(loginUser))
            return "/users/loginForm";

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
    
    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id, String title, String contents ,HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findOne(id);
        if (!question.isSameWriter(loginUser))
            return "/users/loginForm";

        question.update(title, contents);
        qnaRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findOne(id);
        if (!question.isSameWriter(loginUser))
            return "/users/loginForm";

        qnaRepository.delete(id);
        return "redirect:/";
    }
}
