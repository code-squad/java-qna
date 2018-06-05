package codesquad.web;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping()
    public String questions(String title, String contents, HttpSession session, Model model) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findOne(id);
        log.debug("this is answers : {}", Arrays.toString(answerRepository.findByQuestionId(id).toArray()));
        model.addAttribute("question", question);

        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        Question deleteQuestion = questionRepository.findOne(id);
        deleteQuestion.delete();
        questionRepository.save(deleteQuestion);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updateQuestion, HttpSession session, Model model) {
        User updateUser = SessionUtils.getUserFromSession(session);
        updateQuestion.setWriter(updateUser);
        log.debug("update question : {}", updateQuestion);

        Result result = valid(session, updateQuestion);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        Question oldQuestion = questionRepository.findOne(id);
        oldQuestion.update(updateQuestion, updateUser);
        questionRepository.save(oldQuestion);

        return "redirect:/questions/{id}";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        Question updateQuestion = questionRepository.findOne(id);
        model.addAttribute("updateQuestion", updateQuestion);
        return "/qna/updateForm";
    }

    private Result valid(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return Result.NEED_LOGIN;
        }
        return Result.SUCCESS;
    }

    private Result valid(HttpSession session, Question question) {
        Result result = valid(session);
        if (!result.isValid()) {
            return result;
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        if (!question.isMatchedUser(sessionUser)) {
            return Result.MISMATCH_USER;
        }

        return Result.SUCCESS;
    }
}