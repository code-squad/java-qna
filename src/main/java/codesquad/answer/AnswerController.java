package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable long questionId, HttpSession session, String contents) {
        log.debug("create comment");

        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question,HttpSessionUtils.getUserFromSession(session),contents);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
//        return String.format("redirect:/questions/%d", questionId);
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long questionId, @PathVariable long id, HttpSession session, Model model) {
        log.debug("view answer form");

        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = answerRepository.findById(id).orElse(null);
        if(!answer.isMatchWriter(HttpSessionUtils.getUserFromSession(session))) {
            return "/qna/update_failed";
        }

        model.addAttribute("answer", answer);
        return "/qna/answer_update_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, HttpSession session, Answer updatedAnswer) {
        log.debug("update answer");

        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        Answer answer = answerRepository.findById(id).orElse(null);
        if(!answer.isMatchWriter(HttpSessionUtils.getUserFromSession(session))) {
            return "/qna/update_failed";
        }

        answer.update(updatedAnswer);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long questionId, @PathVariable long id, HttpSession session) {
        log.debug("delete comment");

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        Answer answer = answerRepository.findById(id).orElse(null);
        if(!answer.isMatchWriter(HttpSessionUtils.getUserFromSession(session))) {
            return "/qna/update_failed";
        }

        answerRepository.delete(answer);
        return "redirect:/questions/{questionId}";
    }
}
