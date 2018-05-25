package codesquad;

import codesquad.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        //TODO: No such question exception
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);
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
        //TODO: No such question exception
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        logger.debug("Redirecting to /question/edit...");
        return "/questions/edit";
    }

    @PutMapping("/{id}/update")
    public String updateQuestion(HttpSession session, Question updated, @PathVariable Long id) {
        User user = getUserFromSession(session);
        //TODO: No such question exception
        Question question = questionRepository.findOne(id);
        question.updateQuestion(updated, user);
        questionRepository.save(question);
        logger.debug("Question updated!");
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteQuestion(HttpSession session, @PathVariable Long id) {
        User user = getUserFromSession(session);
        //TODO: No such question exception
        //TODO: Prevent unwanted delete/put requests by uri??
        Question question = questionRepository.findOne(id);
        question.flagDeleted(user);
        questionRepository.save(question);
        logger.debug("Question flagged deleted: {}", question);
        return "redirect:/";
    }
}