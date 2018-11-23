package codesquad.question.answer;

import codesquad.config.HttpSessionUtils;
import codesquad.question.QuestionNotFoundException;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import codesquad.user.UserNotFoundException;
import codesquad.user.UserRepository;
import codesquad.utils.TimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String isNotLogin(){
        return "user/login";
    }

    @PostMapping("/{id}")
    public String post(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, String comment){
        if(!HttpSessionUtils.isLogin(session)) return "user/login";
        Answer answer = new Answer(
                questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다.")),
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다.")),
                comment
        );
        answerRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) return "user/login";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findById(answerId).orElse(null);
        if(!sessionedUser.matchId(answer.getUser().getId())) return "qna/error";
        answerRepository.delete(answer);
        return "redirect:/questions/" + questionId;
    }
}
