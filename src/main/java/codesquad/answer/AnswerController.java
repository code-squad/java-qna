package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @PostMapping()
    public String create(HttpSession session, @PathVariable long questionId, String contents) {
        System.out.println("answer 구현");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question,HttpSessionUtils.getUserFormSession(session),contents);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
    }


    @GetMapping("/{id}")
    public String updateForm(@PathVariable long id, HttpSession session, Model model) {
        System.out.println("답변 수정폼");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElse(null);
        if (!answer.matchSessionUser(sessionUser)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            return "redirect:/users/login";
        }
        model.addAttribute("answer",answer);
        return "/qna/updateAnswer";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Answer updatedAnswer) {
        System.out.println("업데이트");
        Answer answer = answerRepository.findById(id).orElse(null);
        answer.update(updatedAnswer);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(HttpSession session, @PathVariable long id) {
        System.out.println("삭제");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElse(null);

        //아이디와 질문아이디와 다를경우 로그아웃 하고 로그인 화면 띄움
        if (!answer.matchSessionUser(sessionUser)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            return "/user/update_failed";
        }
        answerRepository.delete(answer);
        return "redirect:/questions/{questionId}";
    }




}
