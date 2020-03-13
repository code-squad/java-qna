package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private Result valid(HttpSession session, Question question) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }

        return Result.ok();
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
        model.addAttribute("questions", questionRepository.findAllActiveQuestion());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        Optional<Question> optionalQuestion = questionRepository.findActiveQuestionById(id);

        optionalQuestion.orElseThrow(ProductNotfoundException::new);

        List<Answer> answers = answerRepository.findActiveAnswerByQuestionId(id);

        model.addAttribute("question", optionalQuestion.get());
        model.addAttribute("answerList", answers);
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
    public String updateQuestion(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findActiveQuestionById(id);

        optionalQuestion.orElseThrow(ProductNotfoundException::new);

        Question question = optionalQuestion.get();
        Result result = valid(session, question);
        if (!result.isValid()) {
            throw new UnauthorizedException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String putQuestion(@PathVariable("id") Long id, Question updatedQuestion, HttpSession session, Model model) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findActiveQuestionById(id);

        optionalQuestion.orElseThrow(ProductNotfoundException::new);

        Question question = optionalQuestion.get();
        Result result = valid(session, question);
        if (!result.isValid()) {
            throw new UnauthorizedException();
        }

        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findActiveQuestionById(id);

        optionalQuestion.orElseThrow(ProductNotfoundException::new);

        Question question = optionalQuestion.get();
        Result result = valid(session, question);
        if (!result.isValid()) {
            throw new UnauthorizedException();
        }
        if (question.canDelete()) {
            question.delete();
            questionRepository.save(question);
            return "redirect:/";
        }
        throw new UnauthorizedException();
    }
}
