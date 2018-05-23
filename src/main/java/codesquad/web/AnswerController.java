package codesquad.web;


import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String answer(@PathVariable Long questionId, String comment, HttpSession session, Model model) {
        // 만약 클래스의 멤버변수로 다른 클래스의 객체라면? 어떻게 처리할 것인가?
        // 지금은 매개변수로 그냥 나열하지만, 스그니처가 길어지면 가독성 저하된다.
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User writer = SessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionId);
        Answer newAnswer = new Answer(writer, question, comment);

        answerRepository.save(newAnswer);
//        List<Answer> answers = answerRepository.findByQuestionId(questionId);
//        log.debug("answers : {}", Arrays.toString(answers.toArray()));
//        model.addAttribute("answers", answerRepository.findByQuestionId(questionId));
        return "redirect:/questions/{questionId}";
    }
}
