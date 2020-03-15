package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
     private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private QuestionRepostory questionRepostory;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String comment, HttpSession session) throws NotFoundException {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question currentQuestion = questionRepostory.findById(questionId).orElseThrow(() -> new NotFoundException("그런 게시물 없어요"));
        Answer answer = new Answer(sessionUser, currentQuestion, comment);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public String removeAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) throws NotFoundException {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Answer currentAnswer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("그런 댓글 없어요"));
        if (!currentAnswer.matchUser(sessionUser)) {
            throw new IllegalStateException("본인 댓글만 삭제할 수 있어요.");
        }

        answerRepository.delete(currentAnswer);
        currentAnswer.delete();
        return "redirect:/questions/{questionId}";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //Response 헤더의 상태코드값 설정
    protected String catchNotFoundException(Model model, NotFoundException e) {
        log.debug("catchNotFoundException!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected String catchIllegalStateException(Model model, IllegalStateException e) {
        log.debug("catchIllegalStateException!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }
}
