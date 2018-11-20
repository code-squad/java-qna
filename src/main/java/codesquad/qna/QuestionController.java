package codesquad.qna;

import codesquad.user.HttpSessionUtils;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        if(loginUser != null){
            return "qna/form";
        }
        return "user/login";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        System.out.println("excute create!");
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question newQuestion = new Question(loginUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showpage(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "qna/show";
    }

    @PutMapping("/{id}")
    public String modify(Question modifyQuestion, @PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElse(null);
        question.update(modifyQuestion);
        questionRepository.save(question);
        model.addAttribute("list", question);
        return String.format("redirect:/questions/%d", id);
    }

    @GetMapping("/{id}/form")
    public String modifyForm(HttpSession session, Model model, @PathVariable Long id) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if(question.isSameWriter(loginUser)){
            model.addAttribute("modifyForm", question);
            return "qna/modifyForm";
        }
        return "qna/modify_failed";
    }

    @DeleteMapping("/{id}")
    public String delete(String writer, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if(question.isSameWriter(loginUser)) {
            questionRepository.delete(question);
            return "redirect:/";
        }
        return "qna/delete_failed";
    }
}
