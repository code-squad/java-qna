package codesquad.web.controller;

import codesquad.web.domain.model.Question;
import codesquad.web.domain.repository.AnswerRepository;
import codesquad.web.domain.repository.QuestionRepository;
import codesquad.web.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.web.utils.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.web.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @GetMapping("/form")
    public String create(HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        return "/qna/form";
    }

    @PostMapping("")
    public String question(String title, String contents, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
        Question question = Question.builder()
                .writer(sessionUser)
                .contents(contents)
                .title(title).build();
        log.info("Submit Question {} : ", question.toString());
        questionRepository.save(question);
        return "redirect:/questions/" + question.getId();
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        model.addAttribute("answers", answerRepository.findAllByQuestion_Id(id));
        model.addAttribute("answersLength", answerRepository.findAllByQuestion_Id(id).size());
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
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        return questionRepository.findById(id)
                .filter(q -> q.matchWriter((User) session.getAttribute(USER_SESSION_KEY)))
                .map(q -> {
                    questionRepository.deleteById(id);
                    return "redirect:/";
                })
                .orElse("user/login");
    }

    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String showUpdateAnswerPage(@PathVariable("questionId") Long qId, @PathVariable("answerId") Long aId, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        return answerRepository.findById(aId)
                .filter(a -> a.matchWriter((User) session.getAttribute(USER_SESSION_KEY)))
                .map(a -> {
                    answerRepository.deleteById(aId);
                    return "redirect:/questions/" + qId;
                })
                .orElseThrow(() -> new IllegalStateException("cannot change other's answer"));
    }

}
