package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.Result;
import codesquad.answer.Answer;
import codesquad.answer.AnswerRepository;
import codesquad.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(HttpSession session, String title, String contents) {
        log.debug("create : {}", title);

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        questionRepository
                .save(new Question(HttpSessionUtils.getUserFromSession(session), title, contents));
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        log.debug("view question list");

        model.addAttribute("questions", questionRepository.findByDeleted(false));
        return "index";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        log.debug("view question form");

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable long id, Model model) {
        log.debug("view question number {}", id);

        Question question = questionRepository.findById(id).orElse(null);
        List<Answer> answers = answerRepository.findByQuestionIdAndDeleted(id, false);

        model.addAttribute("answers", answers);
        model.addAttribute("countOfAnswers", answers.size());
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        log.debug("view question number {} update form", id);

        Question question = questionRepository.findById(id).orElse(null);
        Result result = valid(session, question);

        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        model.addAttribute("question", question);
        return "/qna/update_form";
    }


    @PutMapping("/{id}")
    public String update(@PathVariable long id, HttpSession session, Model model, Question updatedQuestion) {
        log.debug("update question {}", id);

        Question question = questionRepository.findById(id).orElse(null);
        Result result = valid(session, question);

        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        question.update(HttpSessionUtils.getUserFromSession(session), updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session, Model model) {
        log.debug("delete question {}", id);

        Question question = questionRepository.findById(id).orElse(null);
        Result result = valid(session, question);

        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        question.delete();
        questionRepository.save(question);

        return "redirect:/questions";
    }

    private Result valid(HttpSession session, Question question) {
        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        if(!question.isMatchWriter(loggedInUser)) {
            return Result.fail("작성자만 수정, 삭제가 가능합니다.");
        }

        return Result.ok();
    }
}
