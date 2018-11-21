package codesquad.question;

import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(loginUser.getUserId());
        questionRepository.save(question);
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String questionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{pId}")
    public String questionContents(Model model, @PathVariable long pId) {
        model.addAttribute("question", questionRepository.findById(pId).get());
        return "/qna/show";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (HttpSessionUtils.isLoginUser(session)) {
            return "/qna/form";
        }
        return "user/login";
    }

    @GetMapping("/{pId}/form")
    public String questionUpdateForm(Model model, @PathVariable long pId, HttpSession session) {
        Question question = questionRepository.findById(pId).get();
        model.addAttribute("question", question);
        if (!HttpSessionUtils.isValid(session, question)) {
            return "/qna/update_failed";
        }
        return "/qna/updateForm";
    }

    @PutMapping("/{pId}/update")
    public String questionUpdate(Question updateQuestion, @PathVariable long pId) {
        Question question = questionRepository.findById(pId).get();
        question.update(updateQuestion);
        questionRepository.save(question);
        return "redirect:/question/{pId}";
    }

    @DeleteMapping("/{pId}/delete")
    public String questionDelete(Model model, @PathVariable long pId, HttpSession session) {
        Question question = questionRepository.findById(pId).get();
        model.addAttribute("question", question);
        if (!HttpSessionUtils.isValid(session, question)) {
            return "/qna/update_failed";
        }
        questionRepository.delete(question);
        return "redirect:/";
    }
}