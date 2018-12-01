package codesquad.question;

import codesquad.aspect.SessionCheck;
import codesquad.config.HttpSessionUtils;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String question(@SessionCheck HttpSession session, Model model) {
        return "qna/form";
    }

    @PostMapping("")
    public String postQuestion(@SessionCheck HttpSession session, Model model, Question question) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        question.setUser(sessionedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = getQuestion(id);
        Result result = question.valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, Question updatedQuestion, HttpSession session, Model model) {
        Question question = getQuestion(id);
        Result result = question.update(updatedQuestion, session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        questionRepository.save(question);
        return String.format("redirect:/questions/%s", id);

    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable long id, Model model) {
        Question question = getQuestion(id);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session, Model model) {
        Question question = getQuestion(id);
        Result result = question.deleted(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        questionRepository.save(question);
        return "redirect:/";
    }

    private Question getQuestion(@PathVariable long id) {
        return questionRepository.findById(id).
                orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다."));
    }
}
