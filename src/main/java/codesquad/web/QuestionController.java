package codesquad.web;

import codesquad.domain.*;
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
    public String goForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String saveQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        question.setWriter(user);
        questionRepository.save(question);
        log.debug("Question : {}", question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String searchQuestion(@PathVariable Long id, HttpSession session, Model model) {
        Question question = questionRepository.findOne(id);
        Result result = isValid(session, question);
        log.debug("Question Search{}", question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}/form")
    public String updateQuestion(@PathVariable Long id, Question question, HttpSession session, Model model) {
        log.debug("Question new {}", question);
        if (!HttpSessionUtils.isLoginUser(session)) {
            Result result = Result.fail("You can't update, Please Login");
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        Question beforeQuestion = questionRepository.findOne(id);
        User loginedUser = HttpSessionUtils.getSessionedUser(session);
        Result result = beforeQuestion.update(question, loginedUser);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        questionRepository.save(beforeQuestion);
        log.debug("Question Update{}", beforeQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Question question = questionRepository.findOne(id);
        Result result = isValid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        questionRepository.delete(id);
        log.debug("Question Delete");
        return "redirect:/";
    }

    private Result isValid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("You can't delete, Please Login");
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        if (!user.isSameWriter(question)) {
            return Result.fail("You can't delete another user's Question");
        }
        return Result.ok();
    }
}