package codesquad.question;

import codesquad.aspect.LoginCheck;
import codesquad.aspect.WriterCheck;
import codesquad.user.User;
import codesquad.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    UserRepository userRepository;
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
    @LoginCheck
    public String write(HttpSession session, Question question) {
        User user = (User) session.getAttribute(User.SESSION_NAME);
        question.setWriter(user);
        userRepository.save(user);
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{index}")
    public String read(Model model, @PathVariable long index) {
        Optional<Question> maybeQuestion = questionRepository.findById(index);
        if (maybeQuestion.isPresent()) {
            model.addAttribute("question", maybeQuestion.get());
            return "/question/show";
        }
        return "redirect:/error?wrong_access";
    }

    @GetMapping("/{index}/form")
    @LoginCheck
    public String updateForm(HttpSession session, Model model, @PathVariable long index) {
        Optional<Question> maybeQuestion = questionRepository.findById(index);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (maybeQuestion.isPresent() && maybeQuestion.get().isSameWriter(user)) {
            model.addAttribute("question", maybeQuestion.get());
            return "/question/update_form";
        }
        return "redirect:/error?update_form";
    }

    @GetMapping("/form")
    @LoginCheck
    public String writeForm(HttpSession session, Model model) {
        return "/question/form";
    }

    @PutMapping("/{id}")
    @LoginCheck
    @WriterCheck
    public String update(HttpSession session, @PathVariable long id, Question updateQuestion) {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (maybeQuestion.isPresent() && maybeQuestion.get().isSameWriter(user)) {
            updateQuestion.setIndex(maybeQuestion.get());
            questionRepository.save(updateQuestion);
            return "redirect:/";
        }
        return "redirect:/error";
    }

    @DeleteMapping("/{id}")
    @LoginCheck
    public String delete(HttpSession session, @PathVariable long id) {
        Optional<Question> question = questionRepository.findById(id);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (question.isPresent() && question.get().isSameWriter(user)) {
            questionRepository.deleteById(id);
            return "redirect:/";
        }
        return "redirect:/error";
    }

    @PostMapping("/{id}/answers")
    @LoginCheck
    public String insertAnswer(HttpSession session, @PathVariable Long id, Answer answer) {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        answer.setCommenter((User) session.getAttribute(User.SESSION_NAME));
        if (maybeQuestion.isPresent()) {
            answer.setQuestion(maybeQuestion.get());
            answerRepository.save(answer);
            return "redirect:/questions/" + id;
        }
        return "redirect:/error";
    }

}
