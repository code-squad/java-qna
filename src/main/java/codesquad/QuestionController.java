package codesquad;

import codesquad.model.Question;
import codesquad.model.QuestionRepository;
import codesquad.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("questions", questionRepository.findAllByOrderByIdDesc());
        return "index";
    }

    @GetMapping("/question/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);
        logger.debug("Question attribute added to model.");
        return "questions/show";
    }

    @GetMapping("/questionForm")
    public String getQuestionForm(HttpSession session, Model model) {
        if (!userIsLoggedIn(session)) {
            logger.debug("User is NOT logged in.");
            return "redirect:/users/loginForm";
        }

        User user = getUserFromSession(session);
        model.addAttribute(user);
        return "/questions/form";
    }

    @PutMapping("/submit")
    public String submitQuestion(Question question) {
        questionRepository.save(question);
        logger.debug("Question added: {}", question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}/edit")
    public String editQuestion(HttpSession session, @PathVariable Long id, Model model) {
        if (!userIsLoggedIn(session)) {
            logger.debug("User is NOT logged in.");
            //TODO: direct to error page?
            return "redirect:/users/loginForm";
        }

        User user = getUserFromSession(session);
        Question question = questionRepository.findQuestionById(id);
        if (!question.authorAndUserIdMatch(user)) {
            logger.debug("Author and user ID do NOT match.");
            //TODO: direct to error page?
            return "redirect:/";
        }
        model.addAttribute("question", question);
        logger.debug("Redirecting to /question/form...");
        return "/questions/edit";
    }
}
