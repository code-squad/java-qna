package codesquad.question;

import codesquad.user.User;
import codesquad.util.SessionUtil;
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
    public String form() {
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        checkSessionedUser(session);
        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question newQuestion = Question.newInstance(sessionedUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        checkSessionedUser(session);
        checkUserSelf(id, session);

        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/updatedForm";
    }

    @PutMapping("{id}")
    public String update(@PathVariable long id, Question updatedQuestion, HttpSession session) {
        checkSessionedUser(session);
        Question question = checkUserSelf(id, session);

        question.update(updatedQuestion);
        questionRepository.save(question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        checkSessionedUser(session);
        Question question = checkUserSelf(id, session);

        questionRepository.delete(question);
        return "redirect:/questions";
    }

    private String checkSessionedUser(HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) return "redirect:/users/login";
        return null;
    }

    private Question checkUserSelf(@PathVariable long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        User sessionedUser = (User)session.getAttribute(SessionUtil.USER_SESSION_KEY);
        if (!sessionedUser.matchUserId(question.getWriter())) throw new IllegalStateException("You can't edit the other user's question");

        return question;
    }
}