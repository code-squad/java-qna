package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String form(HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }
        return "question/questionForm";
    }

    @PostMapping("")
    public String question(String contents, String title, HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }
        Question question = new Question(user, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") Long questionId, Model model) {
        model.addAttribute("question", findQuestion(questionRepository, questionId));
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        model.addAttribute("answers", answers);
        return "question/show";
    }

    @GetMapping("/{id}/{question.writer.name}/updateForm")
    public String updateForm(@PathVariable Long id,
                             @PathVariable("question.writer.name") String writer,
                             HttpSession httpSession,
                             Model model) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }

        if (user.notMatchWriter(writer)) {
            return "redirect:/users/loginForm";
        }

        model.addAttribute("question", findQuestion(questionRepository, id));
        return "question/updateForm";
    }

    @PutMapping("{id}/{question.writer.name}/update")
    public String update(@PathVariable Long id,
                         @PathVariable("question.writer.name") String writer,
                         String title, String contents,
                         HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }

        if (user.notMatchWriter(writer)) {
            return "redirect:/users/loginForm";
        }

        Question question = findQuestion(questionRepository, id);
        question.update(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}/{question.writer.name}/delete")
    public String delete(@PathVariable Long id,
                         @PathVariable("question.writer.name") String writer,
                         HttpSession httpSession) {

        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }

        if (user.notMatchWriter(writer)) {
            return "redirect:/users/loginForm";
        }

        questionRepository.delete(findQuestion(questionRepository, id));
        return "redirect:/";
    }

    private Question findQuestion(QuestionRepository questionRepository, Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }
}
