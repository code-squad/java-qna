package codesquad.question;

import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Properties;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        question.setWriter((User) session.getAttribute(User.SESSION_NAME));
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{index}")
    public String show(Model model, @PathVariable long index) {
        Optional<Question> question = questionRepository.findById(index);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "/question/show";
        }
        return "redirect:/questions";
    }

    @GetMapping("/{index}/form")
    public String update(Model model, @PathVariable long index, HttpSession session) {
        System.out.println();
        Optional<Question> question = questionRepository.findById(index);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (question.isPresent() && question.get().isSameWriter(user)) {
            model.addAttribute("question", question.get());
            return "/question/update_form";
        }
        return "redirect:/error";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (user == null) {
            return "/users/login";
        }
        return "/question/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, HttpSession session, Question updateQuestion) {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (user == null) {
            return "users/login";
        }
        if (maybeQuestion.isPresent() && maybeQuestion.get().isSameWriter(user)) {
            updateQuestion.setIndex(maybeQuestion.get());
            questionRepository.save(updateQuestion);
            return "redirect:/";
        }
        return "redirect:/error";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        Optional<Question> question = questionRepository.findById(id);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (user == null) {
            return "redirect:/users/login";
        }
        if (question.isPresent() && question.get().isSameWriter(user)) {
            questionRepository.deleteById(id);
            return "redirect:/";
        }
        return "redirect:/error";
    }

    @PostMapping("/{id}/answers")
    public String insertAnswer(@PathVariable Long id, Answer answer, HttpSession session) {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        answer.setCommenter((User) session.getAttribute(User.SESSION_NAME));
        answerRepository.save(answer);

        Question question = maybeQuestion.get();
        question.addAnswer(answer);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

}
