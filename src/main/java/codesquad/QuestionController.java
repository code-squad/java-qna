package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.exceptions.UnauthorizedRequestException;
import codesquad.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

import static codesquad.HttpSessionUtils.*;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("questions", questionRepository.findAllByOrderByQuestionIdDesc());
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String getQuestion(@PathVariable Long questionId, Model model) {
        Question question = questionRepository.findOne(questionId);
        model.addAttribute("question", question);
        return "questions/show";
    }

    @GetMapping("/questionForm")
    public String getQuestionForm(HttpSession session, Model model) {
        try {
            User user = getUserFromSession(session);
            model.addAttribute("user", user);
            return "/questions/form";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        }

    }

    @PutMapping("/submit")
    public String submitQuestion(HttpSession session, Question question) {
        try {
            User user = getUserFromSession(session);
            question.setAuthor(user);
            questionRepository.save(question);
            logger.debug("Question added: {}", question);
            return "redirect:/";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "/users/login";
        }
    }

    @GetMapping("/questions/{questionId}/edit")
    public String editQuestion(HttpSession session, @PathVariable Long questionId, Model model) {
        try {
            User user = getUserFromSession(session);
            Question question = questionRepository.findQuestionByQuestionId(questionId);
            model.addAttribute("question", question);
            model.addAttribute("user", user);
            logger.debug("Redirecting to /question/edit...");
            return "/questions/edit";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        } catch (UnauthorizedRequestException e) {
            logger.debug(e.getMessage());
            return "/questions/error";
        }
    }

    @PutMapping("/questions/{questionId}/update")
    public String updateQuestion(HttpSession session, Question updated, @PathVariable Long questionId) {
        try {
            User user = getUserFromSession(session);
            Question question = questionRepository.findQuestionByQuestionId(questionId);
            question.updateQuestion(updated, user);
            questionRepository.save(question);
            logger.debug("Question updated!");
            return "redirect:/questions/" + questionId;
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        } catch (UnauthorizedRequestException e) {
            logger.debug(e.getMessage());
            return "/questions/error";
        }
    }

    @DeleteMapping("/questions/{questionId}/delete")
    public String deleteQuestion(HttpSession session, @PathVariable Long questionId) {
        try {
            User user = getUserFromSession(session);
            Question question = questionRepository.findQuestionByQuestionId(questionId);
            if (question.authorAndUserIdMatch(user)) {
                throw new UnauthorizedRequestException("question.userId.mismatch");
            }
            questionRepository.delete(question);
            logger.debug("Question deleted: {}", question);
            return "redirect:/";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        } catch (UnauthorizedRequestException e) {
            logger.debug(e.getMessage());
            return "/questions/error";
        }
    }
}