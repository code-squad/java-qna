package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
// 답변의 경우에는 질문에 종속적이기 때문에 다음과 같이 mapping하였습니다.
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    // 답변 생성하는 메소드 입니다.
    // session에서 해당 유저의 이름으로 답변을 달게 하였습니다.
    // 답변은 별도의 answerRepo에 저장하도록 하였습니다.
    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login_failed";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = questionRepository.getOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    // 답변을 삭제하는 메소드입니다.
    // loginUser와 답변의 작성자가 일치하면 답변을 삭제할 수 있도록 구현하였습니다.
    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        Answer answer = answerRepository.getOne(answerId);
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (answer.authorizeUser(loginUser)) {
            answerRepository.deleteById(answerId);
            return String.format("redirect:/questions/%d", questionId);
        }
        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }
}
