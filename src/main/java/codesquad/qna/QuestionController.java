package codesquad.qna;

import codesquad.HttpSessionUtils;
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

    @PostMapping
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) return "/user/login";
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = Question.newInstance(loginUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/questions";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) return "/user/login";
        return "/qna/form";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable Long index, Model model) {
        model.addAttribute("question", questionRepository.findById(index).orElse(null));
        return "/qna/show";
    }

    @GetMapping("/{index}/form")
    public String updateForm(@PathVariable Long index, Model model,  HttpSession session) {
        checkLoginUser(session);
        Question question = checkUserSelf(index, session);

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{index}")
    public String update(@PathVariable Long index, Question updatedQuestion, HttpSession session) {
        checkLoginUser(session);
        Question question = checkUserSelf(index, session);

        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long index, HttpSession session) {
        checkLoginUser(session);
        Question question = checkUserSelf(index, session);

        questionRepository.delete(question);
        return "redirect:/questions";
    }

    private Question checkUserSelf(@PathVariable Long index, HttpSession session) {
        Question question = questionRepository.findById(index).orElse(null);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!loginUser.matchUserId(question.getWriter())) {
            throw new IllegalStateException("You can't edit the other user's question");
        }
        return question;
    }

    private String checkLoginUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) return "redirect:/user/login";
        return null;
    }
}
