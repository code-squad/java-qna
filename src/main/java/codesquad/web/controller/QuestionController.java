package codesquad.web.controller;

import codesquad.web.domain.Question;
import codesquad.web.domain.QuestionRepository;
import codesquad.web.domain.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static codesquad.web.utils.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.web.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/qna")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    private final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @GetMapping("/form")
    public String create(HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        return "/qna/form";
    }

    @PostMapping("")
    public String question(String title, String contents, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        User sessionUSer = (User) session.getAttribute(USER_SESSION_KEY);
        Question question = new Question(sessionUSer.getUserId(), title, contents);
        log.info("Submit Question {} : ", question.toString());
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String showQuestionUpdatePage(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        if (!id.equals(((User) session.getAttribute(USER_SESSION_KEY)).getId())) {
            throw new IllegalStateException("Cannot update other's question");
        }
        model.addAttribute("question", questionRepository.findById(id)
                .orElseThrow(RuntimeException::new));
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable("id") Long id, Question updateQuestion, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        if (!id.equals(((User) session.getAttribute(USER_SESSION_KEY)).getId())) {
            throw new IllegalStateException("Cannot update other's question");
        }
        questionRepository.findById(id).ifPresent((q) -> {
            q.update(updateQuestion);
            questionRepository.save(q);
        });
        return "redirect:/qna/" + id;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String DeleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        return questionRepository.findById(id)
                .filter(q -> q.matchWriterId(((User) session.getAttribute(USER_SESSION_KEY)).getId()))
                .map(q -> {
                    questionRepository.deleteById(id);
                    return "redirect:/";
                })
                .orElse("user/login");
    }

}
