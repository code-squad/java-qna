package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions")
public class ApiAnswerController {
    private final Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/{questionId}/answers")
    public Answer create(@PathVariable Long questionId,
                         String contents,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession);
            User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);
            Question question = findQuestionById(questionId);
            Answer answer = new Answer(question, contents, sessionedUser);
            question.addAnswer();
            return answerRepository.save(answer);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    @GetMapping("/{questionId}/answers/{answerId}/{writer}/form")
    public ModelAndView updateForm(@PathVariable Long questionId,
                                   @PathVariable Long answerId,
                                   @PathVariable String writer,
                                   HttpSession httpSession,
                                   Model model) {
        try {
            hasPermission(httpSession, writer);
            model.addAttribute("question", findQuestionById(questionId));
            model.addAttribute("answer", findAnswerById(answerId));
            return new ModelAndView("/answer/updateForm");
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/user/login");
        }
    }

    @PutMapping("/{questionId}/answers/{answerId}")
    public ModelAndView update(@PathVariable Long answerId,
                               String contents,
                               HttpSession httpSession,
                               Model model) {
        try {
            hasPermission(httpSession);
            Answer answer = findAnswerById(answerId);
            answer.update(contents);
            answerRepository.save(answer);
            return new ModelAndView("redirect:/questions/{questionId}");
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("/user/login");
        }
    }

    @DeleteMapping("/{questionId}/answers/{answerId}/{writer}")
    public Result delete(@PathVariable Long answerId,
                         @PathVariable String writer,
                         @PathVariable Long questionId,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession, writer);
            Answer answer = findAnswerById(answerId);
            answer.delete();
            Question question = questionRepository.findById(questionId).orElseThrow(() ->
                    new IllegalStateException("There is no answer"));
            question.deleteAnswer();
            answerRepository.save(answer);
            return Result.ok();
        } catch (IllegalStateException e) {
            return Result.fail("작성자만 삭제할 수 있습니다.");
        }
    }

    private void hasPermission(HttpSession httpSession, String writer) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            throw new IllegalStateException("일치하는 아이디가 없습니다.");
        }

        if (sessionedUser.notMatchWriter(writer)) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }
    }

    private void hasPermission(HttpSession httpSession) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
    }

    private Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new IllegalStateException("There is no answer"));
    }

    private Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }
}
