package codesquad.base.qna;

import codesquad.base.Result;
import codesquad.base.user.HttpSessionUtils;
import codesquad.base.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QnaController {

    @Autowired
    private QnaRepository qnaRepository;

    @GetMapping("/form")
    public String qnaForm(HttpSession session) {

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        qnaRepository.save(newQuestion);
        return "redirect:/";
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (question.isSameUserName(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 가능합니다.");
        }
        return Result.ok();
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("quest", qnaRepository.findById(id).orElseGet(null));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = qnaRepository.findById(id).orElseGet(null);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
        Question question = qnaRepository.findById(id).orElseGet(null);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        question.update(title, contents);
        qnaRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Question question = qnaRepository.findById(id).orElseGet(null);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        qnaRepository.delete(qnaRepository.findById(id).orElseGet(null));
        return "redirect:/";
    }
}
