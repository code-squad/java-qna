package com.codessquad.qna.question;

import com.codessquad.qna.constants.CommonConstants;
import com.codessquad.qna.error.exception.QuestionNotFoundException;
import com.codessquad.qna.user.User;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class QuestionController {

    private static Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/")
    public String goIndexPage(Model model) {
        model.addAttribute("questions", questionRepository.findAllByIsDeletedFalseOrderByCreatedDateTimeDesc());
        return "main";
    }

    @GetMapping("/questions/form")
    public String goQuestionForm() {
        return "questions/form";
    }

    @PostMapping("/questions")
    public String createQuestion(HttpSession session, @RequestParam String title, @RequestParam String contents) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        Question question = new Question(loginUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable long id, Model model, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = getQuestionIfExist(id);
        List<Answer> answers = answerRepository.findByQuestionIdAndIsDeletedFalse(id);

        model.addAttribute("question", question);
        model.addAttribute("isLoginUserEqualsWriter", question.isWrittenBy(loginUser));
        model.addAttribute("answers", answers);

        return "questions/show";
    }

    @GetMapping("/questions/{id}/form")
    public String goQuestionModifyForm(@PathVariable long id, Model model, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        Question question = getQuestionIfExist(id);
        if (!question.isWrittenBy(loginUser)) {
            return "redirect:/questions/" + id;
        }

        model.addAttribute("question", question);

        return "questions/modify-form";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable long id,
                                 HttpSession session,
                                 @RequestParam String title,
                                 @RequestParam String contents) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        Question question = getQuestionIfExist(id);
        if (!question.isWrittenBy(loginUser)) {
            return "redirect:/questions/" + id;
        }

        question.updateQuestionData(title, contents, LocalDateTime.now());
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}")
    @Transactional
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }
        Question question = getQuestionIfExist(id);
        if (!question.isWrittenBy(loginUser)) {
            return "redirect:/questions/" + id;
        }
        if (question.isDeletable()) {
            answerRepository.deleteAnswersInQuestion(question);
            questionRepository.save(question.delete());
            return "redirect:/";
        }
        return "redirect:/questions/" + id;
    }

    private Question getQuestionIfExist(long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("해당 질문글을 찾을 수 없습니다."));
    }

}
