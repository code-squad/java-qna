package codesquad;

import codesquad.exceptions.PageNotFoundException;
import codesquad.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static codesquad.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", getValidQuestion(id));
        return "questions/show";
    }

    @GetMapping("/questionForm")
    public String getQuestionForm(HttpSession session, Model model) {
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        return "/questions/form";
    }

    @PutMapping("/submit")
    public String submitQuestion(HttpSession session, Question question) {
        User user = getUserFromSession(session);
        question.setUser(user);
        questionRepository.save(question);
        logger.debug("Question added: {}", question);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editQuestion(HttpSession session, @PathVariable Long id, Model model) {
        User user = getUserFromSession(session);
        Question question = getValidQuestion(id);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        logger.debug("Redirecting to /question/edit...");
        return "/questions/edit";
    }

    @PutMapping("/{id}")
    public String updateQuestion(HttpSession session, Question updated, @PathVariable Long id) {
        User user = getUserFromSession(session);
        Question question = getValidQuestion(id);
        question.updateQuestion(updated, user);
        questionRepository.save(question);
        logger.debug("Question updated!");
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(HttpSession session, @PathVariable Long id) {
        User user = getUserFromSession(session);
        //TODO: Prevent unwanted delete/put requests by uri??
        Question question = getValidQuestion(id);
        question.flagDeleted(user);
        questionRepository.save(question);
        logger.debug("Question flagged deleted: {}", question);
        return "redirect:/";
    }

    private Question getValidQuestion(Long id) throws PageNotFoundException {
        Optional<Question> maybeQuestion = questionRepository.findQuestionById(id);
        if (!maybeQuestion.isPresent()) {
            throw new PageNotFoundException("404: Page Not Found");
        }
        return maybeQuestion.get();
    }
}