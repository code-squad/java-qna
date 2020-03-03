package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String createAnswer(@PathVariable("questionId") Long questionId, HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        optionalQuestion.ifPresent(question -> {
            answer.setQuestion(question);
            answer.setWriter(HttpSessionUtils.getUserFromSession(session));
            answerRepository.save(answer);
        });

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("questions/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        optionalAnswer.ifPresent(answer -> {
            if (HttpSessionUtils.getUserFromSession(session).matchId(optionalAnswer.get().getWriter().getId())) {
                answerRepository.deleteById(id);
            }
        });

        return "redirect:/questions/" + questionId;
    }

    @PutMapping("/questions/{questionId}/answers/{id}")
    public String updateAnswer(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session, Answer updatedAnswer) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        optionalAnswer.ifPresent(answer -> {
            if (HttpSessionUtils.getUserFromSession(session).matchId(optionalAnswer.get().getWriter().getId())) {
                answer.update(updatedAnswer);
                answerRepository.save(answer);
            }
        });

        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/qna/form")
    public String question(Question question, HttpSession session) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        User loginedUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(loginedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Answer[] answers = answerRepository.findByQuestionId(id);

        model.addAttribute("question", optionalQuestion.get());
        model.addAttribute("answers", answers);
        model.addAttribute("answerLength", answers.length);
        return "qna/show";
    }

    @GetMapping("/question")
    public String createQuestion(HttpSession session, Model model) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("writer", HttpSessionUtils.getUserFromSession(session).getName());
        return "/qna/form";
    }

    @GetMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable("id") Long id, HttpSession session, Model model) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Question question = optionalQuestion.get();
        if (!HttpSessionUtils.getUserFromSession(session).matchId(question.getWriter().getId())) {
            throw new IllegalAccessException("자신이 올린 게시글만 수정할 수 있습니다.");
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String putQuestion(@PathVariable("id") Long id, Question updatedQuestion, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Question question = optionalQuestion.get();
        if (!HttpSessionUtils.getUserFromSession(session).matchId(question.getWriter().getId())) {
            throw new IllegalAccessException("자신이 올린 게시글만 수정할 수 있습니다.");
        }

        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Question question = optionalQuestion.get();
        if (!HttpSessionUtils.getUserFromSession(session).matchId(question.getWriter().getId())) {
            throw new IllegalAccessException("자신이 올린 게시글만 삭제할 수 있습니다.");
        }

        questionRepository.delete(question);
        return "redirect:/";
    }
}
