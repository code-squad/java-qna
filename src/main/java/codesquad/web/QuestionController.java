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
        model.addAttribute("question", qnaRepository.findOne(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question question = qnaRepository.findOne(id);
            hasPermission(session, question);
            model.addAttribute("question", question);
            return "/qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new IllegalStateException("로그인이 필요합니다.");
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(loginUser))
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        return true;
    }
    
    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id, String title, String contents ,HttpSession session, Model model) {
        try {
            Question question = qnaRepository.findOne(id);
            hasPermission(session, question);
            question.update(title, contents);
            qnaRepository.save(question);
            return String.format("redirect:/questions/%d", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Question question = qnaRepository.findOne(id);
            hasPermission(session, question);
            model.addAttribute("question", question);
            qnaRepository.delete(id);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }
}
