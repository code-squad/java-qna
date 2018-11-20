

package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.answer.AnswerRepository;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String questions(HttpSession session,Model model) {
        System.out.println("질문하기");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        model.addAttribute("User",HttpSessionUtils.getUserFormSession(session));
        return "/qna/form";
    }

    @PostMapping("")
    public String questions(String title,String contents, HttpSession session) {
        System.out.println("qna 인스턴스 생성");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Question newQuestion = new Question(sessionUser,title,contents);

        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        System.out.println("질문 상세 페이지");
        Question question = questionRepository.findById(id).orElse(null);

        model.addAttribute("answers",answerRepository.findByQuestion(question));
        model.addAttribute("count",((Collection)answerRepository.findByQuestion(question)).size());
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable long id, HttpSession session) {
        System.out.println("질문 수정폼");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(id).orElse(null);

        // 아이디가 다를경우 로그아웃 해서 로그인창 띄움 중복 리펙토링 하자
        if (!question.matchWrite(sessionUser)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            return "redirect:/users/login";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question newQuestion) {
        System.out.println("업데이트");
        Question question = questionRepository.findById(id).orElse(null);
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(HttpSession session, @PathVariable long id){
        System.out.println("삭제");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(id).orElse(null);

        //아이디와 질문아이디와 다를경우 로그아웃 하고 로그인 화면 띄움
        if (!question.matchWrite(sessionUser)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            return "/user/update_failed";
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

}
