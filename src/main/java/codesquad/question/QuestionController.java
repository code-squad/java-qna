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
    public String question(HttpSession session, Model model) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        return "qna/form";
    }

    @PostMapping("")
    public String postQuestion(Question question, HttpSession session, Model model) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        question.setUser(sessionedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = getQuestion(id);
        Result result = valid(session, question);
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
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        question.update(updatedQuestion);
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
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        question.deleted();
        questionRepository.save(question);
        return "redirect:/";
    }

    private Result valid(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        return Result.ok();
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(sessionedUser)) {
            return Result.fail("다른 사람의 글을 수정 또는 삭제할 수 없습니다.");
        }
        return Result.ok();
    }

    private Question getQuestion(@PathVariable long id) {
        return questionRepository.findById(id).
                orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다."));
    }
}
