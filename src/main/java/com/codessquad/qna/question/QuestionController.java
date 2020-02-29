package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonConstants;
import com.codessquad.qna.user.User;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        model.addAttribute("questions", questionRepository.findAllByOrderByCreatedDateTimeDesc());
        return "main";
    }

    @GetMapping("/questions/form")
    public String goQuestionForm() {
        return "questions/form";
    }

    @PostMapping("/questions")
    public String createQuestion(HttpSession session, @RequestParam String title, @RequestParam String contents) {
        User loginUser = getLoginUser(session);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }
        Question question = new Question(loginUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable long id, Model model, HttpSession session) {
        try {
            User loginUser = getLoginUser(session);
            Question question = getQuestionIfExist(id);
            List<Answer> answers = answerRepository.findByQuestionId(id);
            model.addAttribute("question", question);
            model.addAttribute("isLoginUserEqualsWriter", question.isWrittenBy(loginUser));
            model.addAttribute("answers", answers);
        } catch (NotFoundException e) {
            return CommonConstants.ERROR_QUESTION_NOT_FOUND;
        }

        return "questions/show";
    }

    @GetMapping("/questions/{id}/form")
    public String goQuestionModifyForm(@PathVariable long id, Model model, HttpSession session) {
        try {
            User loginUser = getLoginUser(session);
            if (loginUser == null) {
                return CommonConstants.REDIRECT_LOGIN_PAGE;
            }
            Question question = getQuestionIfExist(id);
            if (!question.isWrittenBy(loginUser)) {
                return "redirect:/questions/" + id;
            }
            model.addAttribute("question", question);
        } catch (NotFoundException e) {
            return CommonConstants.ERROR_QUESTION_NOT_FOUND;
        }

        return "questions/modify-form";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable long id,
                                 HttpSession session,
                                 @RequestParam String title,
                                 @RequestParam String contents) {
        try {
            User loginUser = getLoginUser(session);
            if (loginUser == null) {
                return CommonConstants.REDIRECT_LOGIN_PAGE;
            }
            Question question = getQuestionIfExist(id);
            if (!question.isWrittenBy(loginUser)) {
                return "redirect:/questions/" + id;
            }
            question.updateQuestionData(title, contents, LocalDateTime.now());
            questionRepository.save(question);
        } catch (NotFoundException e) {
            return CommonConstants.ERROR_QUESTION_NOT_FOUND;
        }
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        try {
            User loginUser = getLoginUser(session);
            if (loginUser == null) {
                return CommonConstants.REDIRECT_LOGIN_PAGE;
            }
            Question question = getQuestionIfExist(id);
            if (!question.isWrittenBy(loginUser)) {
                return "redirect:/questions/" + id;
            }
            if (question.isDeletable()) {
                answerRepository.deleteAll(question.getAnswers());
                questionRepository.delete(question);
                return "redirect:/";
            }
        } catch (NotFoundException e) {
            return CommonConstants.ERROR_QUESTION_NOT_FOUND;
        }
        return "redirect:/questions/" + id;
    }

    private User getLoginUser(HttpSession session) {
        return (User) session.getAttribute(CommonConstants.SESSION_LOGIN_USER);
    }

    private Question getQuestionIfExist(long id) throws NotFoundException {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 질문글을 찾을 수 없습니다."));
    }

}
