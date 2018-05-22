package codesquad.controller;

import codesquad.controller.handler.UnAuthorizedException;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionRepo.findAll(Sort.by(Sort.Order.desc("id"))));
        return "index";
    }

    @GetMapping("/questions/form")
    public String getForm(Model model, HttpSession session) {
        model.addAttribute("user", HttpSessionUtils.getUserFromSession(session).get());
        return "/question/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        Optional<User> maybeUser = HttpSessionUtils.getUserFromSession(session);
        question.setUser(maybeUser.get());
        questionRepo.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("question", questionRepo.findById(id).get());
        return "/question/show";
    }

    @GetMapping("/questions/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
        Question question = questionRepo.findById(id).get();
        if (!question.isMatch(HttpSessionUtils.getUserFromSession(session).get())) {
            throw new UnAuthorizedException("");
        }
        model.addAttribute("question", questionRepo.findById(id).get());
        return "/question/edit";
    }

    @PutMapping("/questions/{id}")
    public String update(@PathVariable("id") Long id, Question updateQuestion, HttpSession session) {
        Question question = questionRepo.findById(id).get();
        question.update(HttpSessionUtils.getUserFromSession(session).get(), updateQuestion);
        questionRepo.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        Question question = questionRepo.findById(id).get();
        if (!question.isMatch(HttpSessionUtils.getUserFromSession(session).get())) {
            throw new UnAuthorizedException("question.mismatch.userId");
        }
        questionRepo.delete(question);
        return "redirect:/";
    }
}
