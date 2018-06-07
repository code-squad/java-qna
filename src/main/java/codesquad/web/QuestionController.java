package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.Result;
import codesquad.domain.User;
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
            return "/user/login";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String getBackToIndex(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String getBackToIndex(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        questionRepository.deleteById(id);
        return "redirect:/questions";
    }
}

