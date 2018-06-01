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
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

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

//        try {
//            User sessionUser = SessionUtils.getUserFromSession(session);
//            Question newQuestion = new Question(sessionUser, title, contents);
//            hasPermission(session, newQuestion);
//            questionRepository.save(newQuestion);
//            return "redirect:/";
//        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "/user/login";
//        }
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
            return Result.NEED_LOGIN;
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        if (!question.isMatchedUserId(sessionUser)) {
            return Result.MISMATCH_USER;
        }

        return Result.SUCCESS;
    }

    private boolean hasPermission(HttpSession session, Question question) {
        hasPermission(session);

        User sessionUser = SessionUtils.getUserFromSession(session);
        log.debug("session user is : {}", sessionUser);
        if (!question.isMatchedUserId(sessionUser)) {
            throw new IllegalStateException("글 작성자와 일치하지 않습니다.");
        }

        return true;
    }

    private boolean hasPermission(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return true;
    }

    @GetMapping("/form")
    public String form(HttpSession session, Model model) {
        try {
            // question 객체를 생성해야 hasPermission에서 null예외가 나지 않는다. 일단 중복 허용
            if (!SessionUtils.isLoginUser(session)) {
                throw new IllegalStateException("로그인이 필요합니다.");
            }
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
        log.debug("/questions/form/success");
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findOne(id);
        question.setAnswers(answerRepository.findByQuestionId(id));
        model.addAttribute("question", question);

        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Question deleteQuestion = questionRepository.findOne(id);
            hasPermission(session, deleteQuestion);
            deleteQuestion.delete();
            questionRepository.save(deleteQuestion);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updateQuestion, HttpSession session, Model model) {
        log.debug("update question : {}", updateQuestion);
        try {
            Question oldQuestion = questionRepository.findOne(id);
            User updateUser = SessionUtils.getUserFromSession(session);
            updateQuestion.setWriter(updateUser);
            hasPermission(session, updateQuestion);
            oldQuestion.update(updateQuestion, updateUser);
            questionRepository.save(oldQuestion);
            return "redirect:/questions/{id}";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Question updateQuestion = questionRepository.findOne(id);
            hasPermission(session, updateQuestion);
            model.addAttribute("updateQuestion", updateQuestion);
            return "/qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }
}