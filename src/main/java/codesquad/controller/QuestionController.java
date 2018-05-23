package codesquad.controller;

import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/form")
    public String getForm(Model model, HttpSession session) {
        model.addAttribute("user", HttpSessionUtils.getUserFromSession(session).get());
        return "/question/form";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        Optional<User> maybeUser = HttpSessionUtils.getUserFromSession(session);
        question.setUser(maybeUser.get());
        questionRepo.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        Optional<Question> maybeQuestion = questionRepo.findById(id);
        if (!maybeQuestion.isPresent() || maybeQuestion.get().isDeleted()) {
            throw new ForbiddenRequestException("question.not.exist");
        }
        model.addAttribute("question", maybeQuestion.get());
        return "/question/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
        Question question = questionRepo.findById(id).get();
        if (!question.isMatch(HttpSessionUtils.getUserFromSession(session).get())) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }
        model.addAttribute("question", questionRepo.findById(id).get());
        return "/question/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, Question updateQuestion, HttpSession session) {
        Question question = questionRepo.findById(id).get();
        question.update(HttpSessionUtils.getUserFromSession(session).get(), updateQuestion);
        questionRepo.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        Question question = questionRepo.findById(id).get();
        question.delete(HttpSessionUtils.getUserFromSession(session).get(), id);
        questionRepo.save(question);
        return "redirect:/";
    }
}
