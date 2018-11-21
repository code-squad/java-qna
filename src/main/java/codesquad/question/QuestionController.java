package codesquad.question;

import codesquad.config.HttpSessionUtils;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String question(HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) return "redirect:/user/login";
        return "qna/form";
    }

    @PostMapping("")
    public String post(Question question, HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) {
            return "redirect:/user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        question.setWriter(sessionedUser.getName());
        question.setTime(getTodayDate());
        question.setUser(sessionedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) return "redirect:/user/login";
        Question question = getQuestion(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(!question.matchUser(sessionedUser.getId())) {
            return "qna/error";
        };
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) return "redirect:/user/login";
        Question question = getQuestion(id);
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Question question = getQuestion(id);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        Question question = getQuestion(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(HttpSessionUtils.isLogin(session) && question.matchUser(sessionedUser.getId())) {
            questionRepository.delete(question);
            return "redirect:/";
        }
        return "redirect:/user/login";
    }

    private Question getQuestion(@PathVariable Long id) {
        return questionRepository.findById(id).
                    orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다."));
    }

    private String getTodayDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + time.format(today);
    }

}
