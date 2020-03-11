package com.codessquad.qna;

import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
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
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String form(HttpSession httpSession, Model model) {
        try {
            hasPermission(httpSession);
            logger.debug("This is DEBUG Log!");
            return "question/questionForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PostMapping("/create")
    public String create(String contents, String title,
                         HttpSession httpSession, Model model) {
        try {
            hasPermission(httpSession);
            User user = HttpSessionUtils.getUserFromSession(httpSession);
            Question question = new Question(user, title, contents);
            questionRepository.save(question);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") Long questionId, Model model) {
        model.addAttribute("question", findQuestionById(questionRepository, questionId));
        model.addAttribute("answers", answerRepository.findByQuestionIdAndDeletedFalse(questionId));
        return "question/show";
    }

    @GetMapping("/{id}/{question.writer.name}/updateForm")
    public String updateForm(@PathVariable Long id,
                             @PathVariable("question.writer.name") String writer,
                             HttpSession httpSession,
                             Model model) {
        try {
            hasPermission(httpSession, writer);
            model.addAttribute("question", findQuestionById(questionRepository, id));
            return "question/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("{id}/{question.writer.name}/update")
    public String update(@PathVariable Long id,
                         @PathVariable("question.writer.name") String writer,
                         String title, String contents,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession, writer);
            Question question = findQuestionById(questionRepository, id);
            question.update(title, contents);
            questionRepository.save(question);
            return "redirect:/questions/{id}";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}/{question.writer.name}/delete")
    public String delete(@PathVariable Long id,
                         @PathVariable("question.writer.name") String writer,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession, writer);
            Question question = findQuestionById(questionRepository, id);
            if (question.isNoAnswers() || question.isSameBetweenWritersOfAnswers()) {
                question.delete();
                questionRepository.save(question);
                return "redirect:/";
            }
            return "redirect:/questions/{id}";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private Question findQuestionById(QuestionRepository questionRepository, Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }

    private void hasPermission(HttpSession httpSession, String writer) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            throw new IllegalStateException("로그인한 사용자만 가능합니다.");
        }
        if (user.notMatchWriter(writer)) {
            throw new IllegalStateException("글 작성자만 가능합니다.");
        }
    }

    private void hasPermission(HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);
        if (!HttpSessionUtils.isLoginUser(user)) {
            throw new IllegalStateException("로그인한 사용자만 가능합니다.");
        }
    }
}
