package codesquad.web;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

// RestController를 사용을 하면 data의 타입을 json형태로 바꿔준다
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    // Answer는 항상 Question에 속해있다. 따라서 Question이 없으면 Answer 또한 없다. 종속관계에 있다. 항상 question ID가 필요하다.

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return null;

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long id, @PathVariable Long questionId, HttpSession session, Model model) {
        Answer answer = answerRepository.findOne(id);
        Result result = valid(session, questionRepository.findOne(questionId));
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        answerRepository.delete(id);
        return String.format("redirect:/questions/%d", questionId);
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session))
            return Result.fail("로그인이 필요합니다.");
        return Result.ok();
    }
}
