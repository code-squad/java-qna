package codesquad.question;

import codesquad.answer.AnswerRepository;
import codesquad.exception.Result;
import codesquad.user.User;
import codesquad.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String form(Model model, HttpSession session) {
        Result resultOfSessioned = valid(session);
        if (isValid(model, resultOfSessioned)) return "/user/login";

        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, Model model, HttpSession session) {
        Result resultOfSessioned = valid(session);
        if (isValid(model, resultOfSessioned)) return "/user/login";

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question newQuestion = Question.newInstance(sessionedUser, title, contents);
        log.debug("newQuestion : {}", newQuestion);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        log.info("list");
        model.addAttribute("questions", questionRepository.findByDeleted(false));

        return "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        log.info("show");
        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/show";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        Result resultOfSessioned = valid(session);
        if (isValid(model, resultOfSessioned)) return "/user/login";

        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/updatedForm";
    }

    @PutMapping("{id}")
    public String update(@PathVariable long id, Model model, Question updatedQuestion, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        Result resultOfSessioned = valid(session);
        if (isValid(model, resultOfSessioned)) return "/user/login";

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Result result = question.update(updatedQuestion, sessionedUser);
        if (isValid(model, result)) return "/user/login";

        questionRepository.save(question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        Result resultOfSessioned = valid(session);
        if (isValid(model, resultOfSessioned)) return "/user/login";

        model.addAttribute("question", questionRepository.findById(id).get());
        User sessionedUser = SessionUtil.getUserFromSession(session);
        Result result = question.delete(sessionedUser);
        if (isValid(model, result)) return "/qna/show";

        questionRepository.save(question);

        return "redirect:/questions";
    }

    private boolean isValid(Model model, Result result) {
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result valid(HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }
        return Result.success();
    }
}