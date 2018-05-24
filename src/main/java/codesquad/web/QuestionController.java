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
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping()
    public String questions(String title, String contents, HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        log.debug("/questions/form/success");
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        model.addAttribute("answers", answerRepository.findByQuestionId(id));
        model.addAttribute("answersCount", answerRepository.findByQuestionId(id).size());

        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User deletingUser = SessionUtils.getUserFromSession(session);
        Question deleteQuestion = questionRepository.findOne(id);

        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        deleteQuestion.isMatchedUserId(deletingUser);
        questionRepository.delete(id);

        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updateQuestion, HttpSession session, Model model) {
        log.debug("update user : {}", updateQuestion);
        User updateUser = SessionUtils.getUserFromSession(session);
        Question oldQuestion = questionRepository.findOne(id);
        updateQuestion.setWriter(updateUser);

        oldQuestion.update(updateQuestion, updateUser);
        questionRepository.save(oldQuestion);

        return "redirect:/questions/{id}";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        User updateUser = SessionUtils.getUserFromSession(session);
        Question updateQuestion = questionRepository.findOne(id);
        updateQuestion.isMatchedUserId(updateUser);

        model.addAttribute("updateQuestion", updateQuestion);

        return "/qna/updateForm";
    }
}