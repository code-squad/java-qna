package codesquad.question;

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
    public String question(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) return "user/login";
        return "qna/form";
    }

    @PostMapping("")
    public String postQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) return "user/login";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        question.setUser(sessionedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = getQuestion(id);
        try {
            hasPermssion(session, question);
            model.addAttribute("question", question);
            return "qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question updatedQuestion, HttpSession session, Model model) {
        Question question = getQuestion(id);
        try {
            hasPermssion(session, question);
            question.update(updatedQuestion);
            questionRepository.save(question);
            return String.format("redirect:/questions/%s", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question question = getQuestion(id);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session, Model model) {
        Question question = getQuestion(id);
        try {
            hasPermssion(session, question);
            questionRepository.delete(question);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }

    void hasPermssion(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLogin(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(sessionedUser)) {
            throw new IllegalStateException("다른 사람의 글을 수정 또는 삭제할 수 없습니다.");
        }
    }

    private Question getQuestion(@PathVariable Long id) {
        return questionRepository.findById(id).
                orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다."));
    }
}
