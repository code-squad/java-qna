package codesquad.web;

import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
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

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String goForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String saveQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }

        User user = HttpSessionUtils.getSessionedUser(session);
        question.setWriter(user);
        questionRepository.save(question);
        log.debug("Question : {}", question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String searchQuestion(@PathVariable Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }

        Question question = questionRepository.findOne(id);
        User user = HttpSessionUtils.getSessionedUser(session);
        if (!user.isSameWriter(question)) {
            throw new IllegalStateException("You can't update another user's Question");
        }

        log.debug("Question Search{}", question);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}/form")
    public String updateQuestion(@PathVariable Long id, Question question, HttpSession session) {
        log.debug("Question new {}", question);
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("You can't update, Please Login");
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        Question beforeQuestion = questionRepository.findOne(id);
        try {
            beforeQuestion.update(question, user);
            questionRepository.save(beforeQuestion);
            log.debug("Question Update{}", beforeQuestion);
            return "redirect:/";
        }catch (IllegalStateException e){
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("You can't delete, Please Login");
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        Question question = questionRepository.findOne(id);

        if (!user.isSameWriter(question)) {
            throw new IllegalStateException("You can't delete another user's Question");
        }

        questionRepository.delete(id);
        log.debug("Question Delete");
        return "redirect:/";
    }
}