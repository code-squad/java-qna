package codesquad.qna;

import codesquad.user.HttpSessionUtils;
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

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        if (loginUser != null) {
            return "qna/form";
        }
        return "user/login";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        System.out.println("excute create!");
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question newQuestion = new Question(loginUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showpage(@PathVariable Long id, Model model) {
        model.addAttribute("question", getQuestionFromId(id));
        return "qna/show";
    }

    @PutMapping("/{id}")
    public String modify(Question modifyQuestion, @PathVariable Long id, Model model, HttpSession session) {
        Question question = getQuestionFromId(id);
        Result result = valid(session, question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        question.update(modifyQuestion);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @GetMapping("/{id}/form")
    public String modifyForm(HttpSession session, Model model, @PathVariable Long id) {
        Question question = getQuestionFromId(id);
        Result result = valid(session, question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("modifyForm", question);
        return "qna/modifyForm";

    }

    private Question getQuestionFromId(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String delete(String writer, @PathVariable Long id, HttpSession session, Model model) {
        Question question = getQuestionFromId(id);
        Result result = valid(session, question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            model.addAttribute("question", getQuestionFromId(id));
            return "qna/show";
        }

        result = question.deleted();
        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            model.addAttribute("question", getQuestionFromId(id));
            return "qna/show";
        }

        questionRepository.save(question);
        return "redirect:/";
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.failed("로그인이 필요합니다.");
        }

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.failed("자신이 쓴 글만 수정, 삭제할 수 있습니다.");
        }
        return Result.ok();
    }
}
