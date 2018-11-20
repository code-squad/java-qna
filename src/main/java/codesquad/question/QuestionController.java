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
        System.out.println("sessionedUser : " + sessionedUser);
        question.setUser(sessionedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{index}/form")
    public String showUpdateForm(@PathVariable Long index, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) return "redirect:/user/login";
        Question question = getQuestion(index);

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        System.out.println("sessionedUser : " + sessionedUser); // for debug
        if(!question.matchUser(sessionedUser)) {
            return "qna/error";
        };
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{index}")
    public String updateQuestion(@PathVariable Long index, Question updatedQuestion, HttpSession session) {
        if(!HttpSessionUtils.isLogin(session)) return "redirect:/user/login";
        Question question = getQuestion(index);
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + index;
    }

    @GetMapping("/{index}")
    public String detail(@PathVariable Long index, Model model) {
        Question question = getQuestion(index);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @DeleteMapping("/{index}")
    public String delete(@PathVariable Long index, HttpSession session) {
        Question question = getQuestion(index);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(HttpSessionUtils.isLogin(session) && question.matchUser(sessionedUser)) {
            questionRepository.delete(question);
            return "redirect:/";
        }
        return "redirect:/user/login";
    }

    private Question getQuestion(@PathVariable Long index) {
        return questionRepository.findById(index).
                    orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다."));
    }

    private String getTodayDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + time.format(today);
    }

}
